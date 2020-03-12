package com.clstephenson.investmentrebalancer.context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class ContextFactory {

    private static volatile Context instance;

    public static Context getContext(String filename) {
        if (instance != null) {
            return instance;
        }
        synchronized (Context.class) {
            if (instance == null) {
                instance = loadDataFromFile(filename);
            }
        }
        return instance;
    }

    private static Context loadDataFromFile(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        File dataFile = new File(filename);
        if (dataFile.canRead()) {
            try {
                createBackupOfExistingDataFile(dataFile);
                instance = objectMapper.readValue(dataFile, Context.class);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load data from " + dataFile.getName(), e);
            }
        } else {
            instance = new Context();
        }
        instance.setDataFile(dataFile);
        return instance;
    }

    private static void createBackupOfExistingDataFile(File dataFile) {
        try (BufferedInputStream inputStream =
                     new BufferedInputStream(new FileInputStream(dataFile));
             BufferedOutputStream outputStream =
                     new BufferedOutputStream(new FileOutputStream(new File(dataFile.getName() + ".bak")))
        ) {
            int data;
            while ((data = inputStream.read()) != -1) {
                outputStream.write(data);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Could not create backup file " + dataFile.getName(), e);
        }
    }

}
