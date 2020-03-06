package com.clstephenson.investmentrebalancer.commandrunner;

import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.commandrunner.commands.Command;

import java.util.Optional;

public class CommandBuilder {

    private AvailableCommands commandType;
    private Holdings holdings;
    private CommandOptions commandOptions;

    public CommandBuilder() {
        this.commandType = null;
        this.holdings = null;
        this.commandOptions = null;
    }

    public CommandBuilder setHoldings(Holdings holdings) {
        this.holdings = holdings;
        return this;
    }

    public CommandBuilder setCommandInput(String commandInput) throws InvalidCommandException {
        String primaryCommandInput;
        CommandOptions commandOptions = null;
        if (commandInput.contains("-") && commandInput.contains(" ")) {
            primaryCommandInput = commandInput.substring(0, commandInput.indexOf(" ")).toLowerCase();
            commandOptions = CommandOptions.getCommandOptions(commandInput);
        } else {
            primaryCommandInput = commandInput.trim();
        }

        this.commandType = AvailableCommands.getCommandFromInstruction(primaryCommandInput)
                .orElseThrow(() -> new InvalidCommandException(primaryCommandInput + " is not a valid command."));
        this.commandOptions = commandOptions;
        return this;
    }

    public Optional<Command> buildCommand() {
        return Optional.of(Command.createCommand(this.commandType, this.holdings, this.commandOptions));
    }

}
