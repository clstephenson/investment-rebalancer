package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands.ADD_ASSET;

public class ShowAssetDetails extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException, InvalidOptionsException {

        StringBuilder output = new StringBuilder();

        if (getHoldings() == null) {
            throw new InvalidCommandArgsException("ShowAssetDetails requires Holdings object to run.");
        }

        if (getHoldings().getHoldings().isEmpty()) {
            output.append("There are no holdings yet. Use the following command to add one...\n");
            output.append(ADD_ASSET.getSyntaxHelp());
        } else {
            List<Asset> assets = getAssetsFromHoldings(getHoldings());
            List<Asset> matchedAssets = new ArrayList<>();
            if (getCommandOptions() != null) {
                getCommandOptions().getOptionValue("n")
                        .ifPresent(s -> {
                            assets.stream()
                                    .filter(asset -> asset.getName().equalsIgnoreCase(s))
                                    .forEach(matchedAssets::add);
                        });
            }

            if (matchedAssets.isEmpty()) {
                output.append(buildOutputString(getHoldings(), assets));
            } else {
                output.append(buildOutputString(getHoldings(), matchedAssets));
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
