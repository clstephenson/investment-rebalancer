package com.clstephenson.portfoliorebalancer.commands;

public class InvalidCommandException extends Exception {

    public InvalidCommandException() {
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
