package com.clstephenson.investmentrebalancer.commands;

public class InvalidCommandException extends Exception {

    public InvalidCommandException() {
        this("Invalid Command");
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
