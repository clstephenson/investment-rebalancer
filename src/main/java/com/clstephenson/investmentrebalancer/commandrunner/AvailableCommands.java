package com.clstephenson.investmentrebalancer.commandrunner;

import java.util.Arrays;
import java.util.Optional;

public enum AvailableCommands {

    SHOW_HOLDINGS("cmd.showHoldings", "syntax.showHoldings"),
    SHOW_ASSET_DETAILS("cmd.showAssetDetails", "syntax.showAssetDetails"),
    SHOW_ASSET_CLASSES("cmd.showAssetClasses", "syntax.showAssetClasses"),
    SHOW_TARGET_ASSET_MIX("cmd.showTargetMix", "syntax.showTargetMix"),

    ADD_HOLDING("cmd.addHolding", "syntax.addHolding"),
    ADD_OR_UPDATE_ASSET("cmd.updateAsset", "syntax.updateAsset"),

    UPDATE_HOLDING("cmd.updateHolding", "syntax.updateHolding"),
    UPDATE_ASSET_MIX("cmd.updateAssetMix", "syntax.updateAssetMix"),
    UPDATE_TARGET_ASSET_MIX("cmd.updateTargetMix", "syntax.updateTargetMix"),

    DELETE_HOLDING("cmd.deleteHolding", "syntax.deleteHolding"),
    DELETE_ASSET("cmd.deleteAsset", "syntax.deleteAsset"),

    BALANCE("cmd.balance", "syntax.balance"),
    EXIT_PROGRAM("cmd.exit", "syntax.exit");

    private final String commandKey;
    private final String syntaxKey;

    AvailableCommands(String commandKey, String syntaxKey) {
        this.commandKey = commandKey;
        this.syntaxKey = syntaxKey;
    }

    public static boolean matchesAvailableCommand(String text) {
        return Arrays.stream(AvailableCommands.values())
                .map(AvailableCommands::getCommandKey)
                .anyMatch(s -> s.equals(text));
    }

    public static Optional<AvailableCommands> getCommandFromCommandKey(String commandKey) {
        return Arrays.stream(AvailableCommands.values())
                .filter(command -> command.getCommandKey().equals(commandKey))
                .findFirst();
    }

    public String getCommandKey() {
        return commandKey;
    }

    public String getSyntaxKey() {
        return syntaxKey;
    }
}
