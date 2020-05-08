package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
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

        String syntax = getContext().getStringResource(getCommandType().getSyntaxKey());

        if (getCommandOptions() == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        String assetName = getCommandOptions().getOptionValue("n")
                .orElseThrow(() -> new InvalidOptionsException("asset name missing", syntax));

        if (assetName == null || assetName.isEmpty()) {
            String message = "Invalid command options.";
            throw new InvalidOptionsException(message, syntax);
        }

        Asset assetToDelete = getContext().getAssets().getAssetMatching(a -> a.getName().equalsIgnoreCase(assetName))
                .orElseThrow(() ->
                        new InvalidOptionsException("Invalid asset. Use the 'assets' command to show available assets."));

        if (getContext().getHoldings().deleteHoldingsThatMatch(holding -> holding.getAsset().equals(assetToDelete))) {
            output.append("Deleted holdings matching the specified asset.\n");
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
