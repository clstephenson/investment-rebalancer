package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

public class DeleteHolding extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException, InvalidOptionsException {

        if (getContext().getHoldings() == null) {
            throw new InvalidCommandArgsException("DeleteHolding requires Holdings object to run.");
        }

        String syntax = getContext().getStringResource(getCommandType().getSyntaxKey());

        if (getCommandOptions() == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        int index = 0;
        try {
            index = Integer.parseInt(
                    getCommandOptions().getOptionValue("i")
                            .orElseThrow(() -> new InvalidOptionsException("missing holding number", syntax))
            );
        } catch (NumberFormatException e) {
            throw new InvalidOptionsException("Invalid holding. Use the 'holdings' command to see holding numbers.");
        }

        Holding holdingToDelete = getContext().getHoldings().getHoldingAtIndex(index)
                .orElseThrow(() ->
                        new InvalidOptionsException("Invalid holding. Use the 'holdings' command to see holding numbers."));

        return getContext().getHoldings().deleteHolding(holdingToDelete) ?
                String.format(
                        "%s holding with %s shares deleted.",
                        holdingToDelete.getAsset().getName(),
                        holdingToDelete.getNumberOfShares().toString()) :
                "No assets were deleted.";
    }
}
