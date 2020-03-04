package com.clstephenson.portfoliorebalancer;

import com.clstephenson.portfoliorebalancer.commands.*;

import java.util.Arrays;
import java.util.Scanner;

import static com.clstephenson.portfoliorebalancer.commands.AvailableCommands.values;

public class PortfolioRebalancer {

    private static Holdings myHoldings = new Holdings();

    public static void main(String... args) {

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        while (true) {

            printPrompt();

            String userInput = scanner.next();

            try {
                String result = new CommandBuilder()
                        .setHoldings(myHoldings)
                        .setCommandInput(userInput)
                        .build().orElseThrow(() -> new InvalidCommandException("Could not execute the command."));
                sendMessageToOutput("");
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

}
