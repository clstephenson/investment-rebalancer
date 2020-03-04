package com.clstephenson.portfoliorebalancer.commands;

import com.clstephenson.portfoliorebalancer.Holdings;

import java.util.HashMap;
import java.util.List;

public abstract class Command {

    public static String processCommand(String userInput, Holdings holdings, HashMap<String, List<String>> assetClasses)
            throws InvalidCommandException, InvalidOptionsException, InvalidCommandArgsException {
        String commandInput;
        CommandOptions commandOptions = null;

        if (userInput.contains("-") && userInput.contains(" ")) {
            commandInput = userInput.substring(0, userInput.indexOf(" ")).toLowerCase();
            commandOptions = CommandOptions.getCommandOptions(userInput);
        } else {
            commandInput = userInput.trim();
        }

        AvailableCommands command = null;
        command = AvailableCommands.getCommandFromInstruction(commandInput)
                .orElseThrow(() -> new InvalidCommandException(String.format("Unknown command: %s", commandInput)));

        String commandOutput = null;

        //todo: create builder class for commands
        switch (command) {
            case LIST_ASSETS:
                commandOutput = new ListAssets().run(holdings, assetClasses, commandOptions);
                break;
            case LIST_ASSET_CLASSES:
                commandOutput = new ListAssetClasses().run(null, assetClasses, commandOptions);
                break;
            case ADD_ASSET:
                commandOutput = new AddAsset().run(holdings, null, commandOptions);
                break;
            case DELETE_ASSET:
                commandOutput = new DeleteAsset().run(holdings, null, commandOptions);
                break;
            case UPDATE_ASSET:
                commandOutput = new UpdateAsset().run(holdings, null, commandOptions);
                break;
            case BALANCE:
                commandOutput = new BalanceAssets().run(holdings, assetClasses, commandOptions);
                break;
            case EXIT_PROGRAM:
                new ExitProgram().run(null, null, null);
                break;
        }

        return commandOutput;
    }


    public abstract String run(Holdings holdings, HashMap<String, List<String>> assetClasses, CommandOptions commandOptions)
            throws InvalidCommandArgsException, InvalidOptionsException;
}
