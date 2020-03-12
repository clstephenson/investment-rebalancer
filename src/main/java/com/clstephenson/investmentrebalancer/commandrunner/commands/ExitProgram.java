package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.context.ContextPersistenceException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ExitProgram extends Command {

    @Override
    public String run() throws ContextPersistenceException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(getContext().getDataFile(), getContext());
        } catch (IOException e) {
            throw new ContextPersistenceException("Unable to save the context to " + getContext().getDataFile().getName(), e);
        }
        System.exit(0);
        return "";
    }
}
