package com.clstephenson.investmentrebalancer.commands;

import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.Holdings;

public class DeleteAsset extends Command {

    @Override
    public String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException, InvalidOptionsException {

        if (holdings == null) {
            throw new InvalidCommandArgsException("DeleteAsset requires Holdings object to run.");
        }

        String syntax = AvailableCommands.DELETE_ASSET.getSyntaxHelp();

        if (commandOptions == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        int index = 0;
        try {
            index = Integer.parseInt(
                    commandOptions.getOptionValue("i")
                            .orElseThrow(() -> new InvalidOptionsException("missing asset number", syntax))
            );
        } catch (NumberFormatException e) {
            throw new InvalidOptionsException("Invalid asset. Use the 'list' command to show available asset numbers.");
        }

        Holding holdingToDelete = holdings.getHoldingAtIndex(index)
                .orElseThrow(() ->
                        new InvalidOptionsException("Asset option must be a number. Use the 'list' command to show available asset numbers."));

        return holdings.deleteHolding(holdingToDelete) ?
                String.format(
                        "%s asset with %s shares deleted.",
                        holdingToDelete.getAsset().getName(),
                        holdingToDelete.getNumberOfShares().toString()) :
                "No assets were deleted.";
    }
}
