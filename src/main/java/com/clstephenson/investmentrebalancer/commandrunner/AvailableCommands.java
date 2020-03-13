package com.clstephenson.investmentrebalancer.commandrunner;

import java.util.Arrays;
import java.util.Optional;

public enum AvailableCommands {

    SHOW_HOLDINGS("holdings", "holdings\nholdings -n [asset name]"),
    SHOW_ASSET_DETAILS( "assets", "assets\nassets -n [asset name]"),
    SHOW_ASSET_CLASSES("classes", "classes"),
    SHOW_TARGET_ASSET_MIX("target", "target"),

    ADD_HOLDING("addholding", "addholding -n [asset name] -s [number of shares]"),
    ADD_OR_UPDATE_ASSET("updateasset", "updateasset -n [asset name] -p [share price]"),

    UPDATE_HOLDING("updateholding", "updateholding -i [holding ID from list] -s [number of shares]"),
    UPDATE_ASSET_MIX("updatemix", "updatemix -n [asset name]"),
    UPDATE_TARGET_ASSET_MIX("updatetarget", "updatetarget"),

    DELETE_HOLDING("deleteholding", "delete -i [holding ID from list]"),
    DELETE_ASSET("deleteasset", "delete -n [asset name]"),

    BALANCE("balance", ""),
    EXIT_PROGRAM("exit", "exit")
    ;

    private final String command;
    private final String syntax;

    AvailableCommands(String commandLineInstruction, String syntax) {
        this.command = commandLineInstruction;
        this.syntax = syntax;
    }

    public static boolean matchesAvailableCommand (String text) {
        return Arrays.stream(AvailableCommands.values())
                .map(AvailableCommands::getCommandLineInstruction)
                .anyMatch(s -> s.equals(text));
    }

    public static Optional<AvailableCommands> getCommandFromInstruction(String commandInput) {
        return Arrays.stream(AvailableCommands.values())
                .filter(command -> command.getCommandLineInstruction().equals(commandInput))
                .findFirst();
    }

    public String getCommandLineInstruction() {
        return command;
    }

    public String getSyntaxHelp() {
        return syntax;
    }
}
