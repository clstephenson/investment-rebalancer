package com.clstephenson.investmentrebalancer.commands;

import com.clstephenson.investmentrebalancer.Holdings;

import java.util.Map;
import java.util.Optional;

public abstract class Command {

    public static Optional<String> processCommand(Map<ValidCommandArgType, Object> commandArgs)
            throws InvalidCommandException, InvalidOptionsException, InvalidCommandArgsException {

        String primaryCommandInput =
                getStringFromArgs(commandArgs.get(ValidCommandArgType.PRIMARY_COMMAND_INPUT))
                        .orElseThrow(InvalidCommandException::new);

        CommandOptions commandOptions =
                getCommandOptionsFromArgs(commandArgs.get(ValidCommandArgType.COMMAND_OPTIONS))
                        .orElseThrow(InvalidCommandException::new);

        AvailableCommands command =
                AvailableCommands.getCommandFromInstruction(primaryCommandInput)
                        .orElseThrow(() -> new InvalidCommandException(String.format("Unknown command: %s", primaryCommandInput)));

        Holdings holdings =
                getHoldingsFromArgs(commandArgs.get(ValidCommandArgType.HOLDINGS))
                        .orElseThrow(InvalidCommandException::new);

        String commandOutput = getCommand(command).get()
                .run(holdings, commandOptions);
        return Optional.ofNullable(commandOutput);
    }

    private static Optional<Command> getCommand(AvailableCommands command) {
        Command returnVal;
        switch (command) {
            case LIST_ASSETS:
                returnVal = new ListAssets();
                break;
            case LIST_ASSET_CLASSES:
                returnVal = new ListAssetClasses();
                break;
            case ADD_ASSET:
                returnVal = new AddAsset();
                break;
            case DELETE_ASSET:
                returnVal = new DeleteAsset();
                break;
            case UPDATE_ASSET:
                returnVal = new UpdateAsset();
                break;
            case BALANCE:
                returnVal = new BalanceAssets();
                break;
            case EXIT_PROGRAM:
                returnVal = new ExitProgram();
                break;
            default:
                returnVal = null;
        }
        return Optional.of(returnVal);
    }

    private static Optional<String> getStringFromArgs(Object arg) {
        Optional<String> returnVal;
        if (arg instanceof String) {
            returnVal = Optional.of((String) arg);
        } else {
            returnVal = Optional.empty();
        }
        return returnVal;
    }

    private static Optional<CommandOptions> getCommandOptionsFromArgs(Object arg) {
        Optional<CommandOptions> returnVal;
        if (arg instanceof CommandOptions) {
            returnVal = Optional.of((CommandOptions) arg);
        } else {
            returnVal = Optional.empty();
        }
        return returnVal;
    }

    private static Optional<Holdings> getHoldingsFromArgs(Object arg) {
        Optional<Holdings> returnVal;
        if (arg instanceof Holdings) {
            returnVal = Optional.of((Holdings) arg);
        } else {
            returnVal = Optional.empty();
        }
        return returnVal;
    }

    public abstract String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException, InvalidOptionsException;

    enum ValidCommandArgType {
        HOLDINGS,
        PRIMARY_COMMAND_INPUT,
        COMMAND_OPTIONS
    }


}
