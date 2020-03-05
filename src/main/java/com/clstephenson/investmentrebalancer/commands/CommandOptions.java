package com.clstephenson.investmentrebalancer.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandOptions {

    private final Map<String, String> options;

    public static CommandOptions getCommandOptions(String commandInput) {
        return new CommandOptions(commandInput);
    }

    private CommandOptions(String commandInput) {
        this.options = parseCommandForOptions(commandInput);
    }

    private Map<String, String> parseCommandForOptions(String commandInput) {
        String[] optionArray = commandInput.substring(commandInput.indexOf("-")).split("-");
        Map<String, String> options = new HashMap<>();
        for (String option : optionArray) {
            if (option.length() > 2) {
                String[] splitOption = option.split(" ", 2);
                options.put(splitOption[0].trim(), splitOption[1].trim());
            }
        }
        return options;
    }

    public Optional<String> getOptionValue(String option) {
        return Optional.ofNullable(options.get(option));
    }

}
