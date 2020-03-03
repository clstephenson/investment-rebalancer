package com.clstephenson.portfoliorebalancer.commands;

import com.clstephenson.portfoliorebalancer.Holdings;
import com.clstephenson.portfoliorebalancer.MajorAssetClass;
import com.clstephenson.portfoliorebalancer.MinorAssetClass;

import java.util.HashMap;
import java.util.List;

public class ListAssetClasses extends Command {

    @Override
    public String run(Holdings holdings, HashMap<String, List<String>> assetClasses, CommandOptions commandOptions)
            throws InvalidCommandArgsException {

        StringBuilder output = new StringBuilder();

        for (MajorAssetClass majorAssetClass : MajorAssetClass.values()) {
            output.append(majorAssetClass.getName()).append("\n");
            for (MinorAssetClass minorAssetClass : MinorAssetClass.values()) {
                if (minorAssetClass.getParentAssetClass().equals(majorAssetClass)) {
                    output.append(String.format("  - %s%n", minorAssetClass.getName()));
                }
            }
        }

        return output.toString();
    }
}
