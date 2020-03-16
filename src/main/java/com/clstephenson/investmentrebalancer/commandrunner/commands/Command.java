package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.AssetMix;
import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.context.Context;
import com.clstephenson.investmentrebalancer.commandrunner.*;
import com.clstephenson.investmentrebalancer.context.ContextPersistenceException;

import java.util.function.UnaryOperator;

public abstract class Command {

    private AvailableCommands commandType;
    private Context context;
    private CommandOptions commandOptions;
    private UnaryOperator<AssetMix> assetMixCallback;

    public static Command createCommand(Context context, AvailableCommands commandType, CommandOptions commandOptions) {
        Command command;
        switch (commandType) {
            case SHOW_HOLDINGS:
                command = new ShowHoldings();
                break;
            case SHOW_ASSET_DETAILS:
                command = new ShowAssetDetails();
                break;
            case SHOW_ASSET_CLASSES:
                command = new ShowAssetClasses();
                break;
            case SHOW_TARGET_ASSET_MIX:
                command = new ShowTargetAssetMix();
                break;
            case ADD_HOLDING:
                command = new AddHolding();
                break;
            case ADD_OR_UPDATE_ASSET:
                command = new UpdateAsset();
                break;
            case UPDATE_HOLDING:
                command = new UpdateHolding();
                break;
            case UPDATE_ASSET_MIX:
                command = new UpdateAssetMix();
                break;
            case UPDATE_TARGET_ASSET_MIX:
                command = new UpdateTargetAssetMix();
                break;
            case DELETE_HOLDING:
                command = new DeleteHolding();
                break;
            case DELETE_ASSET:
                command = new DeleteAsset();
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
        command.setContext(context);
        command.setCommandType(commandType);
        command.setCommandOptions(commandOptions);
        return command;
    }

    public Context getContext() {
        return context;
    }

    public AvailableCommands getCommandType() {
        return commandType;
    }

    public CommandOptions getCommandOptions() {
        return commandOptions;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCommandType(AvailableCommands commandType) {
        this.commandType = commandType;
    }

    public void setCommandOptions(CommandOptions commandOptions) {
        this.commandOptions = commandOptions;
    }

    public UnaryOperator<AssetMix> getAssetMixCallback() {
        return assetMixCallback;
    }

    public void setAssetMixCallback(UnaryOperator<AssetMix> assetMixCallback) {
        this.assetMixCallback = assetMixCallback;
    }

    public abstract String run() throws InvalidCommandArgsException, InvalidOptionsException, InvalidAssetMixPercentageValue, ContextPersistenceException;

}
