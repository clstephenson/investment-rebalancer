package com.clstephenson.portfoliorebalancer.commands;

import com.clstephenson.portfoliorebalancer.Holdings;

public class ExitProgram extends Command {

    @Override
    public String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException {
        System.exit(0);
        return "";
    }
}
