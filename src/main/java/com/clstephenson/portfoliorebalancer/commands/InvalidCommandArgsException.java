package com.clstephenson.portfoliorebalancer.commands;

public class InvalidCommandArgsException extends Exception {
    public InvalidCommandArgsException(String message) {
        super(message);
    }
}
