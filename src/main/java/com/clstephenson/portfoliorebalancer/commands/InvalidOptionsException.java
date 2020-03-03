package com.clstephenson.portfoliorebalancer.commands;

public class InvalidOptionsException extends Exception {

    private final String invalidOption;
    private final String commandSyntax;

    public InvalidOptionsException(String message) {
        this(message, null, null);
    }

    public InvalidOptionsException(String message, String commandSyntax) {
        this(message, commandSyntax, null);
    }

    public InvalidOptionsException(String message, String commandSyntax, String invalidOption) {
        super(message);
        this.invalidOption = invalidOption;
        this.commandSyntax = commandSyntax;
    }

    public String getInvalidOption() {
        return this.invalidOption;
    }

    public String getCommandSyntax() {
        return commandSyntax;
    }
}
