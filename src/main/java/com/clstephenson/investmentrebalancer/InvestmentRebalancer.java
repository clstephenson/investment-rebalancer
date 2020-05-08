package com.clstephenson.investmentrebalancer;

import com.clstephenson.investmentrebalancer.commandrunner.*;
import com.clstephenson.investmentrebalancer.commandrunner.commands.Command;
import com.clstephenson.investmentrebalancer.context.Context;
import com.clstephenson.investmentrebalancer.context.ContextFactory;
import com.clstephenson.investmentrebalancer.context.ContextPersistenceException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.UnaryOperator;

import static com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands.*;

//todo: convert string literals into resources
public class InvestmentRebalancer {

    public static final String DATA_FILE_NAME = "investment-rebalancer.json";
    private static Context context;

    public static void main(String... args) {
        context = ContextFactory.getContext(DATA_FILE_NAME);

        if (System.getenv("INSERT_TEST_DATA") != null && context.getAssets().isEmpty()) {
            insertTestData(context);
        }

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        while (true) {
            printPrompt();
            String userInput = scanner.next();

            try {
                Command command = getCommandFromInput(userInput, context);
                if (command.getCommandType() == UPDATE_ASSET_MIX || command.getCommandType() == UPDATE_TARGET_ASSET_MIX) {
                    UnaryOperator<AssetMix> callback = assetMixValues -> {
                        sendMessageToOutput("Enter new asset mix values...");
                        for (AssetClass assetClass : AssetClass.values()) {
                            sendMessageToOutput(String.format("%s: ", assetClass.getName()), false);
                            String input = scanner.next();
                            BigDecimal percentValue = input.isEmpty() ? BigDecimal.ZERO : new BigDecimal(input);
                            assetMixValues.getMixItems().put(assetClass, percentValue);
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
            } catch (InvalidAssetMixPercentageValue e) {
                sendMessageToOutput("The asset mix was NOT updated!\nEach asset class percentage must be between 0% and 100%, and the total mix should add up to 100%.");
            } catch (ContextPersistenceException e) {
                sendMessageToOutput(String.format("Unable to save the data to %s. Make sure the file is not open.",
                        context.getDataFile().getAbsolutePath()));
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
                .map(availableCommands -> context.getStringResource(availableCommands.getCommandKey()))
                .forEach(InvestmentRebalancer::sendMessageToOutput);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static void insertTestData(Context context) {
        try {
            sendMessageToOutput(getCommandFromInput("updateasset -n stock1 -p 5.00", context).run());
            sendMessageToOutput(getCommandFromInput("updateasset -n bond1 -p 3.00", context).run());
            sendMessageToOutput(getCommandFromInput("addholding -n stock1 -s 250", context).run());
            sendMessageToOutput(getCommandFromInput("addholding -n stock1 -s 50", context).run());
            sendMessageToOutput(getCommandFromInput("addholding -n bond1 -s 100", context).run());
            sendMessageToOutput(getCommandFromInput("addholding -n bond1 -s 500", context).run());
            context.getHoldings().getAssetFromHoldings("stock1").get().getAssetMix().updatePercentageFor(AssetClass.US_STOCKS, BigDecimal.valueOf(100));
            context.getHoldings().getAssetFromHoldings("bond1").get().getAssetMix().updatePercentageFor(AssetClass.US_BONDS, BigDecimal.valueOf(100));
            context.getTargetMix().getAssetMix().updatePercentageFor(AssetClass.US_STOCKS, BigDecimal.valueOf(60));
            context.getTargetMix().getAssetMix().updatePercentageFor(AssetClass.US_BONDS, BigDecimal.valueOf(40));
            sendMessageToOutput(getCommandFromInput("assets", context).run());
            sendMessageToOutput(getCommandFromInput("holdings", context).run());
            sendMessageToOutput(getCommandFromInput("balance", context).run());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Command getCommandFromInput(String userInput, Context context) throws InvalidCommandException {
        return new CommandBuilder()
                .setContext(context)
                .setCommandInput(userInput)
                .buildCommand().orElseThrow(() -> new InvalidCommandException("Invalid Command"));
    }

}
