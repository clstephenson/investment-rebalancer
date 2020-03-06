package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.CommandOptions;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

public abstract class Command {

    private AvailableCommands commandType;
    private Holdings holdings;
    private CommandOptions commandOptions;

    public static Command createCommand(AvailableCommands commandType, Holdings holdings, CommandOptions commandOptions) {
        Command command;
        switch (commandType) {
            case LIST_HOLDINGS:
                command = new ListHoldings();
                break;
            case LIST_ASSET_CLASSES:
                command = new ListAssetClasses();
                break;
            case SHOW_ASSET_DETAILS:
                command = new ShowAssetDetails();
                break;
            case ADD_ASSET:
                command = new AddAsset();
                break;
            case DELETE_ASSET:
                command = new DeleteAsset();
                break;
            case UPDATE_ASSET:
                command = new UpdateAsset();
                break;
            case BALANCE:
                command = new BalanceAssets();
                break;
            case EXIT_PROGRAM:
                command = new ExitProgram();
                break;
            default:
                command = null;
        }
        command.setCommandType(commandType);
        command.setHoldings(holdings);
        command.setCommandOptions(commandOptions);
        return command;
    }

    public AvailableCommands getCommandType() {
        return commandType;
    }

    public Holdings getHoldings() {
        return holdings;
    }

    public CommandOptions getCommandOptions() {
        return commandOptions;
    }

    public void setCommandType(AvailableCommands commandType) {
        this.commandType = commandType;
    }

    public void setHoldings(Holdings holdings) {
        this.holdings = holdings;
    }

    public void setCommandOptions(CommandOptions commandOptions) {
        this.commandOptions = commandOptions;
    }

    public abstract String run() throws InvalidCommandArgsException, InvalidOptionsException;

}
