package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.AssetClass;
import com.clstephenson.investmentrebalancer.AssetClassCategory;
import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UpdateAssetMix extends Command {

    @Override
    public String run() throws InvalidCommandArgsException, InvalidOptionsException {

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

        Map<AssetClass, Double> mix = new HashMap<>();
        getAssetMixCallback().apply(mix);

        for (AssetClass assetClass : AssetClass.values()) {
            double currentPercentage = asset.getAssetMix().getMixPercentageFor(assetClass);
            asset.getAssetMix().updatePercentageFor(
                    assetClass,
                    mix.getOrDefault(assetClass, currentPercentage)
            );
            //todo: need to catch numberformatexception for out of range values
        }

        output.append("Updated asset mix for ")
                .append(assetName);

        return output.toString();
    }
}
