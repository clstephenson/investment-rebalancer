package com.clstephenson.investmentrebalancer;

import com.clstephenson.investmentrebalancer.commandrunner.*;

import java.util.Arrays;
import java.util.Scanner;

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
                String result = handleCommandInput(userInput);
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
            sendMessageToOutput(handleCommandInput("add -n ge -p 3.00 -s 50"));
            sendMessageToOutput(handleCommandInput("add -n ge -p 3.00 -s 1000"));
            sendMessageToOutput(handleCommandInput("add -n agilent -p 56.00 -s 250"));
            sendMessageToOutput(handleCommandInput("add -n agilent -p 56.00 -s 100"));
            sendMessageToOutput(handleCommandInput("holdings"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String handleCommandInput(String userInput) throws
            InvalidCommandException, InvalidCommandArgsException, InvalidOptionsException {
        return new CommandBuilder()
                .setHoldings(myHoldings)
                .setCommandInput(userInput)
                .build().orElseThrow(() -> new InvalidCommandException("Could not execute the command."));
    }

}
