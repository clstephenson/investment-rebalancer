package com.clstephenson.portfoliorebalancer.commands;

import com.clstephenson.portfoliorebalancer.AssetClass;
import com.clstephenson.portfoliorebalancer.AssetClassCategory;
import com.clstephenson.portfoliorebalancer.Holdings;

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
