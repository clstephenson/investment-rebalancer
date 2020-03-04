package com.clstephenson.portfoliorebalancer.commands;

import com.clstephenson.portfoliorebalancer.Holdings;

import java.util.HashMap;
import java.util.List;

public class ExitProgram extends Command {

    @Override
    public String run(Holdings holdings, HashMap<String, List<String>> assetClasses, CommandOptions commandOptions)
            throws InvalidCommandArgsException {
        System.exit(0);
        return "";
    }
}
