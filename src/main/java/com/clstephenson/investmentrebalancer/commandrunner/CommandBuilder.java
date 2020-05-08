package com.clstephenson.investmentrebalancer.commandrunner;

import com.clstephenson.investmentrebalancer.AssetClass;
import com.clstephenson.investmentrebalancer.commandrunner.commands.Command;
import com.clstephenson.investmentrebalancer.context.Context;

import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class CommandBuilder {

    private Context context;
    private String commandInput;
    private AvailableCommands commandType;
    private CommandOptions commandOptions;
    private UnaryOperator<Map<AssetClass, Double>> assetMixCallback;

    public CommandBuilder() {
        this.commandType = null;
        this.commandOptions = null;
        this.commandInput = null;
    }

    public CommandBuilder setCommandInput(String commandInput) {
        this.commandInput = commandInput;
        return this;
    }

    public CommandBuilder setAssetMixCallback(UnaryOperator<Map<AssetClass, Double>> callbackFunction) {
        this.assetMixCallback = callbackFunction;
        return this;
    }

    public CommandBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public Optional<Command> buildCommand() throws InvalidCommandException {
        String primaryCommandInput;
        CommandOptions commandOptions = null;
        if (commandInput.contains("-") && commandInput.contains(" ")) {
            primaryCommandInput = commandInput.substring(0, commandInput.indexOf(" ")).toLowerCase();
            commandOptions = CommandOptions.getCommandOptions(commandInput);
        } else {
            primaryCommandInput = commandInput.trim();
        }

        String commandKey = context.getResourceKeys().stream()
                .filter(key -> key.startsWith("cmd.") && context.getStringResource(key).equalsIgnoreCase(primaryCommandInput))
                .findFirst()
                .orElse(null);

        this.commandType = AvailableCommands.getCommandFromCommandKey(commandKey)
                .orElseThrow(() -> new InvalidCommandException(primaryCommandInput + " is not a valid command."));
        this.commandOptions = commandOptions;

        return Optional.of(Command.createCommand(this.context, this.commandType, this.commandOptions));
    }

}
