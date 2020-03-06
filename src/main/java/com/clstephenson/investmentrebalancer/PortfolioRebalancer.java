package com.clstephenson.investmentrebalancer;

import com.clstephenson.investmentrebalancer.commandrunner.*;
import com.clstephenson.investmentrebalancer.commandrunner.commands.Command;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands.values;

public class PortfolioRebalancer {

    private static Holdings myHoldings = new Holdings();

    public static void main(String... args) {

        if (System.getenv("INSERT_TEST_DATA") != null) {
            insertTestData();
        }

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        while (true) {

            printPrompt();

            String userInput = scanner.next();

            try {
                Command command = getCommandFromInput(userInput);
                if (command.getCommandType() == AvailableCommands.UPDATE_ASSET_MIX) {
                    sendMessageToOutput("Enter new asset mix values...");
                    UnaryOperator<Map<AssetClass, Double>> callback = assetMixValues -> {
                        for (AssetClass assetClass : AssetClass.values()) {
                            sendMessageToOutput(String.format("%s: ", assetClass.getName()), false);
                            String input = scanner.next();
                            assetMixValues.put(assetClass, Double.parseDouble(input));
                        }
                        return assetMixValues;
                    };
                    command.setAssetMixCallback(callback);

                }
                String result = command.run();
                sendMessageToOutput(result);
            } catch (InvalidCommandArgsException e) {
                throw new RuntimeException(e);
            } catch (InvalidCommandException e) {
                sendMessageToOutput(e.getMessage());
                printHelp();
            } catch (InvalidOptionsException e) {
                sendMessageToOutput(e.getMessage());
                sendMessageToOutput(String.format("Command Syntax:%n%s", e.getCommandSyntax()));
            }
        }
    }

    private static void sendMessageToOutput(String message) {
        sendMessageToOutput(message, true);
    }

    private static void sendMessageToOutput(String message, boolean endWithNewLine) {
        if (endWithNewLine) {
            System.out.println(message);
        } else {
            System.out.print(message);
        }
    }

    private static void printPrompt() {
        sendMessageToOutput(String.format("%nrebalancer>"), false);
    }

    private static void printHelp() {
        // todo: add help message
        Arrays.stream(values())
                .map(AvailableCommands::getCommandLineInstruction)
                .forEach(PortfolioRebalancer::sendMessageToOutput);
    }

    private static void insertTestData() {
        try {
            sendMessageToOutput(getCommandFromInput("add -n ge -p 3.00 -s 50").run());
            sendMessageToOutput(getCommandFromInput("add -n ge -p 3.00 -s 1000").run());
            sendMessageToOutput(getCommandFromInput("add -n agilent -p 56.00 -s 250").run());
            sendMessageToOutput(getCommandFromInput("add -n agilent -p 56.00 -s 100").run());
            sendMessageToOutput(getCommandFromInput("holdings").run());
            sendMessageToOutput(getCommandFromInput("assets -n ge").run());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Command getCommandFromInput(String userInput) throws InvalidCommandException {
        return new CommandBuilder()
                .setHoldings(myHoldings)
                .setCommandInput(userInput)
                .buildCommand().orElseThrow(() -> new InvalidCommandException("Invalid Command"));
    }

}
