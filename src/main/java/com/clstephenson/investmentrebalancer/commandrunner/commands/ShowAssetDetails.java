package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands.*;

public class ShowAssetDetails extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException, InvalidOptionsException {

        StringBuilder output = new StringBuilder();

        if (getContext().getAssets() == null) {
            throw new InvalidCommandArgsException("ShowAssetDetails requires Assets object to run.");
        }

        if (getContext().getAssets().isEmpty()) {
            output.append("There are no assets yet. Use the following command to add one...\n");
            output.append(ADD_OR_UPDATE_ASSET.getSyntaxHelp());
        } else {
            Asset matchedAsset = null;

            if (getCommandOptions() != null) {
                String assetName = getCommandOptions().getOptionValue("n")
                        .orElseThrow(() -> new InvalidOptionsException("asset name missing", SHOW_ASSET_DETAILS.getSyntaxHelp()));

                matchedAsset = getContext().getAssets()
                        .getAssetMatching(asset -> asset.getName().equalsIgnoreCase(assetName))
                        .orElse(null);
            }

            if (matchedAsset != null) {
                output.append(buildOutputString(Stream.of(matchedAsset)));
            } else {
                output.append(buildOutputString(getContext().getAssets().stream()));
            }
        }

        return output.toString();
    }

    private String buildOutputString(Stream<Asset> assets) {
        StringBuilder output = new StringBuilder();

        assets.forEach(asset -> {
            BigDecimal totalSharesInHoldings = getContext().getHoldings()
                    .getHoldingsThatMatch(holding -> holding.getAsset().equals(asset))
                    .map(Holding::getNumberOfShares)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

            output.append("\n")
                    .append(String.format("Name: %s\n", asset.getName()))
                    .append(String.format("Symbol: %s\n", asset.getSymbol()))
                    .append(String.format("Price per Share: %s\n", asset.getPricePerShare().toString()))
                    .append(String.format("Total Shares in Holdings: %s\n", totalSharesInHoldings))
                    .append("Asset Mix:\n")
                    .append(asset.getAssetMix().toString());
        });

        return output.toString();
    }
}
