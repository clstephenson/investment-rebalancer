package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.AssetMix;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidAssetMixPercentageValue;

public class UpdateTargetAssetMix extends Command {

    @Override
    public String run() throws InvalidAssetMixPercentageValue {

        StringBuilder output = new StringBuilder();

        AssetMix mix = new AssetMix();
        getAssetMixCallback().apply(mix);
        if (mix.isValid()) {
            getContext().getTargetMix().setAssetMix(mix);
            output.append("Updated target asset mix.");
        }

        return output.toString();
    }

}
