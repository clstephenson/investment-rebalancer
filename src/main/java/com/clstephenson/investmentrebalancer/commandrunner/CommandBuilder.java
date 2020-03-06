package com.clstephenson.investmentrebalancer.commandrunner;

import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.commandrunner.commands.Command;
import com.clstephenson.investmentrebalancer.commandrunner.commands.Command.ValidCommandArgType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandBuilder {

    private final Map<ValidCommandArgType, Object> commandArgs;

    public CommandBuilder() {
        this.commandArgs = new HashMap<>();
    }

    public CommandBuilder setHoldings(Holdings holdings) {
        this.commandArgs.put(ValidCommandArgType.HOLDINGS, holdings);
        return this;
    }

    public CommandBuilder setCommandInput(String commandInput) {
        String primaryCommandInput;
        CommandOptions commandOptions = null;
        if (commandInput.contains("-") && commandInput.contains(" ")) {
            primaryCommandInput = commandInput.substring(0, commandInput.indexOf(" ")).toLowerCase();
            commandOptions = CommandOptions.getCommandOptions(commandInput);
        } else {
            primaryCommandInput = commandInput.trim();
        }
        this.commandArgs.put(ValidCommandArgType.PRIMARY_COMMAND_INPUT, primaryCommandInput);
        this.commandArgs.put(ValidCommandArgType.COMMAND_OPTIONS, commandOptions);
        return this;
    }

    public Optional<String> build() throws InvalidCommandArgsException, InvalidOptionsException, InvalidCommandException {
        return Command.processCommand(commandArgs);
    }
}
