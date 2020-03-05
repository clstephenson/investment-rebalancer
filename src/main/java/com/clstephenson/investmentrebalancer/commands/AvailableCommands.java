package com.clstephenson.investmentrebalancer.commands;

import java.util.Arrays;
import java.util.Optional;

public enum AvailableCommands {

    LIST_HOLDINGS("holdings", "holdings -n [asset name]"),
    LIST_ASSET_CLASSES("classes", "classes"),
    SHOW_ASSET_DETAILS( "assets", "assets -n [asset name]"),
    ADD_ASSET("add", "add -n [asset name] -p [share price] -s [number of shares]"),
    UPDATE_ASSET ("update", "update -i [asset ID from list] -n [asset name] -p [share price] -s [number of shares]"),
    DELETE_ASSET("delete", "delete -i [asset ID from list]"),
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
