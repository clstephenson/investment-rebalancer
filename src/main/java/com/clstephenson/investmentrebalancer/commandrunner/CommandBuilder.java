package com.clstephenson.investmentrebalancer.commandrunner;

import com.clstephenson.investmentrebalancer.AssetClass;
import com.clstephenson.investmentrebalancer.TargetMix;
import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.commandrunner.commands.Command;

import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class CommandBuilder {

    private AvailableCommands commandType;
    private TargetMix target;
    private Holdings holdings;
    private CommandOptions commandOptions;
    private UnaryOperator<Map<AssetClass, Double>> assetMixCallback;

    public CommandBuilder() {
        this.commandType = null;
        this.target = null;
        this.holdings = null;
        this.commandOptions = null;
    }

    public CommandBuilder setHoldings(Holdings holdings) {
        this.holdings = holdings;
        return this;
    }

    public CommandBuilder setTargetAssetMix(TargetMix target) {
        this.target = target;
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

    public CommandBuilder setAssetMixCallback(UnaryOperator<Map<AssetClass, Double>> callbackFunction) {
        this.assetMixCallback = callbackFunction;
        return this;
    }

    public Optional<Command> buildCommand() {
        return Optional.of(Command.createCommand(this.commandType, this.holdings, this.commandOptions, this.target));
    }

}
