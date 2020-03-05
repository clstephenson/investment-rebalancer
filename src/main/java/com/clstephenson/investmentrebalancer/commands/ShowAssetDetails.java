package com.clstephenson.investmentrebalancer.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.Holdings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.clstephenson.investmentrebalancer.commands.AvailableCommands.ADD_ASSET;

public class ShowAssetDetails extends Command {

    @Override
    public String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException, InvalidOptionsException {

        StringBuilder output = new StringBuilder();
        String syntax = AvailableCommands.SHOW_ASSET_DETAILS.getSyntaxHelp();

        if (holdings == null) {
            throw new InvalidCommandArgsException("ShowAssetDetails requires Holdings object to run.");
        }

        if (holdings.getHoldings().isEmpty()) {
            output.append("There are no holdings yet. Use the following command to add one...\n");
            output.append(ADD_ASSET.getSyntaxHelp());
        } else {
            List<Asset> assets = getAssetsFromHoldings(holdings);
            List<Asset> matchedAssets = new ArrayList<>();
            if (commandOptions != null) {
                commandOptions.getOptionValue("n")
                        .ifPresent(s -> {
                            assets.stream()
                                    .filter(asset -> asset.getName().equalsIgnoreCase(s))
                                    .forEach(matchedAssets::add);
                        });
            }

            if (matchedAssets.isEmpty()) {
                output.append(buildOutputString(holdings, assets));
            } else {
                output.append(buildOutputString(holdings, matchedAssets));
            }
        }

        return output.toString();
    }

    private List<Asset> getAssetsFromHoldings(Holdings holdings) {
        return holdings.getHoldings().stream()
                .map(Holding::getAsset)
                .distinct()
                .collect(Collectors.toList());
    }

    private String buildOutputString(Holdings holdings, List<Asset> assets) {
        StringBuilder output = new StringBuilder();

        for (Asset asset : assets) {
            BigDecimal totalSharesInHoldings = holdings.getHoldings().stream()
                    .filter(holding -> holding.getAsset().equals(asset))
                    .map(holding -> holding.getNumberOfShares())
                    .reduce(BigDecimal::add).get();

            output.append(String.format("Name: %s\n", asset.getName()))
                    .append(String.format("Symbol: %s\n", asset.getSymbol()))
                    .append(String.format("Price per Share: %s\n", asset.getPricePerShare().toString()))
                    .append(String.format("Total Shares in Holdings: %s\n", totalSharesInHoldings))
                    .append("Asset Mix:\n")
                    .append(asset.getAssetMix().toString());
        }

        return output.toString();
    }
}
