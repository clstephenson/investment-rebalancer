package com.clstephenson.investmentrebalancer.commands;

public class InvalidCommandArgsException extends Exception {
    public InvalidCommandArgsException(String message) {
        super(message);
    }
}
