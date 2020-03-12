package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ExitProgram extends Command {

    @Override
    public String run() {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("investment-rebalancer.json"), getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
        return "";
    }
}
