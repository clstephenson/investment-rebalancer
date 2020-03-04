package com.clstephenson.portfoliorebalancer.commands;

import com.clstephenson.portfoliorebalancer.Holding;
import com.clstephenson.portfoliorebalancer.Holdings;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class UpdateAsset extends Command {

    @Override
    public String run(Holdings holdings, HashMap<String, List<String>> assetClasses, CommandOptions commandOptions)
            throws InvalidCommandArgsException, InvalidOptionsException {

        if (holdings == null) {
            throw new InvalidCommandArgsException("UpdateAsset requires Holdings object to run.");
        }

        String syntax = AvailableCommands.UPDATE_ASSET.getSyntaxHelp();

        if (commandOptions == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        int index = 0;
        try {
            index = Integer.parseInt(
                    commandOptions.getOptionValue("i")
                            .orElseThrow(() -> new InvalidOptionsException("asset number is required", syntax))
            );
        } catch (NumberFormatException e) {
            throw new InvalidOptionsException("Invalid asset. Use the 'list' command to show available asset numbers.", syntax);
        }

        Holding holdingToUpdate = holdings.getHoldingAtIndex(index)
                .orElseThrow(() ->
                        new InvalidOptionsException("Invalid asset. Use the 'list' command to show available asset numbers."));


        String assetName = commandOptions.getOptionValue("n").orElse(holdingToUpdate.getAsset().getName());
        String sharePrice = commandOptions.getOptionValue("p").orElse(holdingToUpdate.getAsset().getPricePerShare().toString());
        String numberOfShares = commandOptions.getOptionValue("s").orElse(holdingToUpdate.getNumberOfShares().toString());

        holdingToUpdate.getAsset().setName(assetName);
        holdingToUpdate.getAsset().setPricePerShare(new BigDecimal(sharePrice));
        holdingToUpdate.setNumberOfShares(new BigDecimal(numberOfShares));
        return holdingToUpdate.toString();
    }
}
