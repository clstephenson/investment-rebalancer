package com.clstephenson.investmentrebalancer.commands;

import com.clstephenson.investmentrebalancer.Holdings;

public class ExitProgram extends Command {

    @Override
    public String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException {
        System.exit(0);
        return "";
    }
}
