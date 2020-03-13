package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

import java.math.BigDecimal;

public class UpdateHolding extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException, InvalidOptionsException {

        if (getContext().getHoldings() == null) {
            throw new InvalidCommandArgsException("UpdateAsset requires Holdings object to run.");
        }

        String syntax = AvailableCommands.UPDATE_HOLDING.getSyntaxHelp();

        if (getCommandOptions() == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        int index = 0;
        try {
            index = Integer.parseInt(
                    getCommandOptions().getOptionValue("i")
                            .orElseThrow(() -> new InvalidOptionsException("holding number is required", syntax))
            );
        } catch (NumberFormatException e) {
            throw new InvalidOptionsException("Invalid holding. Use the 'holdings' command to see asset numbers.", syntax);
        }

        Holding holdingToUpdate = getContext().getHoldings().getHoldingAtIndex(index)
                .orElseThrow(() ->
                        new InvalidOptionsException("Invalid holding. Use the 'holdings' command to see holding numbers."));


        String numberOfShares = getCommandOptions().getOptionValue("s").orElse(holdingToUpdate.getNumberOfShares().toString());

        holdingToUpdate.setNumberOfShares(new BigDecimal(numberOfShares));
        return holdingToUpdate.toString();
    }
}
