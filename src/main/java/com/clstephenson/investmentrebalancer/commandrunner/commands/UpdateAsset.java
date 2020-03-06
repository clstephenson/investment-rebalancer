package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

import java.math.BigDecimal;

public class UpdateAsset extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException, InvalidOptionsException {

        if (getHoldings() == null) {
            throw new InvalidCommandArgsException("UpdateAsset requires Holdings object to run.");
        }

        String syntax = AvailableCommands.UPDATE_ASSET.getSyntaxHelp();

        if (getCommandOptions() == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        int index = 0;
        try {
            index = Integer.parseInt(
                    getCommandOptions().getOptionValue("i")
                            .orElseThrow(() -> new InvalidOptionsException("asset number is required", syntax))
            );
        } catch (NumberFormatException e) {
            throw new InvalidOptionsException("Invalid asset. Use the 'list' command to show available asset numbers.", syntax);
        }

        Holding holdingToUpdate = getHoldings().getHoldingAtIndex(index)
                .orElseThrow(() ->
                        new InvalidOptionsException("Invalid asset. Use the 'list' command to show available asset numbers."));


        String assetName = getCommandOptions().getOptionValue("n").orElse(holdingToUpdate.getAsset().getName());
        String sharePrice = getCommandOptions().getOptionValue("p").orElse(holdingToUpdate.getAsset().getPricePerShare().toString());
        String numberOfShares = getCommandOptions().getOptionValue("s").orElse(holdingToUpdate.getNumberOfShares().toString());

        holdingToUpdate.getAsset().setName(assetName);
        holdingToUpdate.getAsset().setPricePerShare(new BigDecimal(sharePrice));
        holdingToUpdate.setNumberOfShares(new BigDecimal(numberOfShares));
        return holdingToUpdate.toString();
    }
}
