package com.clstephenson.investmentrebalancer.context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ContextFactory {

    private static volatile Context instance;

    public static Context getContext() {
        if (instance != null) {
            return instance;
        }
        synchronized (Context.class) {
            if (instance == null) {
                File dataFile = new File("investment-rebalancer.json");
                if (dataFile.exists()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        instance = objectMapper.readValue(dataFile, Context.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    instance = new Context();
                }
            }
        }
        return instance;
    }

}
