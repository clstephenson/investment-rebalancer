package com.clstephenson.portfoliorebalancer.commands;

import com.clstephenson.portfoliorebalancer.Asset;
import com.clstephenson.portfoliorebalancer.Holdings;

import java.util.HashMap;
import java.util.List;

import static com.clstephenson.portfoliorebalancer.commands.AvailableCommands.ADD_ASSET;

public class ExitProgram extends Command {

    @Override
    public String run(Holdings holdings, HashMap<String, List<String>> assetClasses, CommandOptions commandOptions)
            throws InvalidCommandArgsException {
        System.exit(0);
        return "";
    }
}
