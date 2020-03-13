package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.Validations;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

import java.math.BigDecimal;

public class UpdateAsset extends Command {

    @Override
    public String run()
            throws InvalidOptionsException {

        String syntax = AvailableCommands.ADD_OR_UPDATE_ASSET.getSyntaxHelp();

        if (getCommandOptions() == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        String assetName = getCommandOptions().getOptionValue("n")
                .orElseThrow(() -> new InvalidOptionsException("asset name missing", syntax));
        String sharePrice = getCommandOptions().getOptionValue("p")
                .orElseThrow(() -> new InvalidOptionsException("share price missing", syntax));
        String assetSymbol = getCommandOptions().getOptionValue("s").orElse("");

        boolean isValid = true;
        if (assetName == null || assetName.isEmpty()) {
            isValid = false;
        } else if (sharePrice == null || !Validations.StringIsDoubleValue(sharePrice)) {
            isValid = false;
        }

        if (!isValid) {
            String message = "Invalid command options.";
            throw new InvalidOptionsException(message, syntax);
        }

        Asset asset = getContext().getAssets()
                .getAssetMatching(a -> a.getName().equalsIgnoreCase(assetName))
                .orElseGet(() -> {
                    Asset newAsset = new Asset(assetName, assetSymbol, new BigDecimal(sharePrice));
                    getContext().getAssets().add(newAsset);
                    return null;
                });
        if (asset != null) {
            asset.setPricePerShare(new BigDecimal(sharePrice));
        }
        return "Updated Asset";
    }
}
