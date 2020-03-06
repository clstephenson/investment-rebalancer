package com.clstephenson.investmentrebalancer.commandrunner;

public class InvalidCommandException extends Exception {

    public InvalidCommandException() {
        this("Invalid Command");
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
