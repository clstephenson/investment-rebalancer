package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

public class DeleteAsset extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException, InvalidOptionsException {

        StringBuilder output = new StringBuilder();

        if (getContext().getAssets() == null) {
            throw new InvalidCommandArgsException("DeleteAsset requires Assets object to run.");
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
            throw new InvalidOptionsException("Invalid asset. Use the 'assets' command to show available asset numbers.");
        }

        Asset assetToDelete = getContext().getAssets().getAssetAtIndex(index)
                .orElseThrow(() ->
                        new InvalidOptionsException("Invalid asset. Use the 'assets' command to show available asset numbers."));

        if (getContext().getHoldings().deleteHoldingsThatMatch(holding -> holding.getAsset().equals(assetToDelete))) {
            output.append("Deleted holding matching the specified asset.\n");
            if (getContext().getAssets().deleteAsset(assetToDelete)) {
                output.append(String.format("Deleted asset %s.", assetToDelete.getName()));
            } else {
                output.append("Deleted asset holdings, but could not delete the asset.\n");
            }
        } else {
            output.append("Unable to delete the asset or its holdings.");
        }

        return output.toString();
    }
}
