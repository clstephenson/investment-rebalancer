package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.commandrunner.CommandOptions;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;

public class ExitProgram extends Command {

    @Override
    public String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException {
        System.exit(0);
        return "";
    }
}
