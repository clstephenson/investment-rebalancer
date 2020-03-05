package com.clstephenson.investmentrebalancer.commands;

import com.clstephenson.investmentrebalancer.AssetClass;
import com.clstephenson.investmentrebalancer.AssetClassCategory;
import com.clstephenson.investmentrebalancer.Holdings;

public class ListAssetClasses extends Command {

    @Override
    public String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException {

        StringBuilder output = new StringBuilder();

        for (AssetClassCategory assetClassCategory : AssetClassCategory.values()) {
            output.append(assetClassCategory.getName()).append("\n");
            for (AssetClass assetClass : AssetClass.values()) {
                if (assetClass.getAssetCategory().equals(assetClassCategory)) {
                    output.append(String.format("  - %s%n", assetClass.getName()));
                }
            }
        }

        return output.toString();
    }
}
