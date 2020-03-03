package com.clstephenson.portfoliorebalancer;

import com.clstephenson.portfoliorebalancer.commands.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.clstephenson.portfoliorebalancer.commands.AvailableCommands.values;

public class PortfolioRebalancer {

    public static final String ASSET_CLASS_FILENAME = "asset-classes.txt";
    public static final String FILE_COMMENT_LINE_PREFIX = "#";

    private static Holdings myHoldings = new Holdings();

    public static void main(String...args) {

        HashMap<String, List<String>> assetClasses;
        try {
            assetClasses = getAssetClassList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        while (true) {

            printPrompt();

            String userInput = scanner.next();

            try {
                String result = Command.processCommand(userInput, myHoldings, assetClasses);
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

    private static HashMap<String, List<String>> getAssetClassList() throws IOException {
        HashMap<String, List<String>> assetClasses = new HashMap<>();

        try (BufferedReader in = new BufferedReader(new FileReader(ASSET_CLASS_FILENAME))) {
            String text;
            while ((text = in.readLine()) != null) {
                if (!text.startsWith(FILE_COMMENT_LINE_PREFIX) && text.trim().length() > 4) {
                    String majorClassName = text.substring(0, text.indexOf("["));
                    String subClasses = text.substring(text.indexOf("[") + 1, text.indexOf("]"));
                    List<String> subClassNames = new ArrayList<>(Arrays.asList(subClasses.split(",")));
                    subClassNames.replaceAll(String::trim);
                    assetClasses.putIfAbsent(majorClassName.trim(), subClassNames);
                }
            }
            return assetClasses;
        } catch (IOException e) {
            throw e;
        }
    }

}
