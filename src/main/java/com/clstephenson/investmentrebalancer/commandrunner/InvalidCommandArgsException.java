package com.clstephenson.investmentrebalancer.commandrunner;

public class InvalidCommandArgsException extends Exception {
    public InvalidCommandArgsException(String message) {
        super(message);
    }
}
