package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

public class DeleteAsset extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException, InvalidOptionsException {

        if (getContext().getHoldings() == null) {
            throw new InvalidCommandArgsException("DeleteAsset requires Holdings object to run.");
        }

        String syntax = AvailableCommands.DELETE_ASSET.getSyntaxHelp();

        if (getCommandOptions() == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        int index = 0;
        try {
            index = Integer.parseInt(
                    getCommandOptions().getOptionValue("i")
                            .orElseThrow(() -> new InvalidOptionsException("missing asset number", syntax))
            );
        } catch (NumberFormatException e) {
            throw new InvalidOptionsException("Invalid asset. Use the 'list' command to show available asset numbers.");
        }

        Holding holdingToDelete = getContext().getHoldings().getHoldingAtIndex(index)
                .orElseThrow(() ->
                        new InvalidOptionsException("Asset option must be a number. Use the 'list' command to show available asset numbers."));

        return getContext().getHoldings().deleteHolding(holdingToDelete) ?
                String.format(
                        "%s asset with %s shares deleted.",
                        holdingToDelete.getAsset().getName(),
                        holdingToDelete.getNumberOfShares().toString()) :
                "No assets were deleted.";
    }
}
