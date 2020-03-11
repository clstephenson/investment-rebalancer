package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;

public class ExitProgram extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException {

        System.exit(0);
        return "";
    }
}
