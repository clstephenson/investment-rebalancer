package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.AssetMix;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidAssetMixPercentageValue;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

public class UpdateAssetMix extends Command {

    @Override
    public String run() throws InvalidCommandArgsException, InvalidOptionsException, InvalidAssetMixPercentageValue {

        StringBuilder output = new StringBuilder();

        if (getHoldings() == null) {
            throw new InvalidCommandArgsException("UpdateMix requires Holdings object to run.");
        }

        String syntax = AvailableCommands.UPDATE_ASSET_MIX.getSyntaxHelp();

        if (getCommandOptions() == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        String assetName = getCommandOptions().getOptionValue("n")
                .orElseThrow(() -> new InvalidOptionsException("asset name is required", syntax));
        Asset asset = getHoldings().getAssetFromHoldings(assetName)
                .orElseThrow(() -> new InvalidOptionsException("Invalid asset", syntax));

        AssetMix mix = new AssetMix();
        getAssetMixCallback().apply(mix);
        asset.setAssetMix(mix);

        output.append("Updated asset mix for ")
                .append(assetName);

        return output.toString();
    }

}
