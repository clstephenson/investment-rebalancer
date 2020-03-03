package com.clstephenson.portfoliorebalancer.commands;

import com.clstephenson.portfoliorebalancer.Asset;
import com.clstephenson.portfoliorebalancer.Holdings;

import java.util.HashMap;
import java.util.List;

public class DeleteAsset extends Command {

    @Override
    public String run(Holdings holdings, HashMap<String, List<String>> assetClasses, CommandOptions commandOptions)
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

        Asset assetToDelete = holdings.getAssetAtIndex(index)
                .orElseThrow(() ->
                        new InvalidOptionsException("Asset option must be a number. Use the 'list' command to show available asset numbers."));

        return holdings.deleteAsset(assetToDelete) ?
                String.format(
                        "%s asset with %s shares deleted.",
                        assetToDelete.getName(),
                        assetToDelete.getNumberOfShares().toString()) :
                "No assets were deleted.";
    }
}
