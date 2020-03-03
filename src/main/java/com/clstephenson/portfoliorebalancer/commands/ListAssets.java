package com.clstephenson.portfoliorebalancer.commands;

import com.clstephenson.portfoliorebalancer.Asset;
import com.clstephenson.portfoliorebalancer.Holdings;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.clstephenson.portfoliorebalancer.commands.AvailableCommands.ADD_ASSET;

public class ListAssets extends Command {

    @Override
    public String run(Holdings holdings, HashMap<String, List<String>> assetClasses, CommandOptions commandOptions)
            throws InvalidCommandArgsException {

        StringBuilder output = new StringBuilder();

        if (holdings == null) {
            throw new InvalidCommandArgsException("ListAssets requires Holdings object to run.");
        }

        if (holdings.getAssets().isEmpty()) {
            output.append("There are no assets yet. Add an asset using the following command...\n");
            output.append(ADD_ASSET.getSyntaxHelp());
        } else {
            Holdings matchedAssets = new Holdings();
            if (commandOptions != null) {
                commandOptions.getOptionValue("n")
                        .ifPresent(s -> {
                            holdings.getAssets().stream()
                                    .filter(asset -> asset.getName().equalsIgnoreCase(s))
                                    .forEach(matchedAssets::add);
                        });
            }

            if (matchedAssets.getAssets().isEmpty()) {
                output.append(buildOutputString(holdings));
            } else {
                output.append(buildOutputString(matchedAssets));
            }
        }

        return output.toString();
    }

    private String buildOutputString (Holdings assets) {
        StringBuilder output = new StringBuilder();

        final String HEADER_ASSET = "Asset";
        final String HEADER_PRICE = "Share Price ($)";
        final String HEADER_SHARES = "Qty Shares";
        final String HEADER_VALUE = "Value ($)";

        int maxNameLength = assets.getAssets().stream()
                .mapToInt(value -> value.getName().length()).max().getAsInt();
        if (HEADER_ASSET.length() > maxNameLength) {
            maxNameLength = HEADER_ASSET.length();
        }

        String nameColumnFormat = "%1$-" + (maxNameLength + 4) + "s";
        String priceColumnFormat = "%2$11.2f";
        String sharesColumnFormat = "%3$11.0f";
        String valueColumnFormat = "%4$11.2f";
        String format = String.format("%s     %s     %s     %s%n",
                nameColumnFormat, priceColumnFormat, sharesColumnFormat, valueColumnFormat);
        output.append(String.format(nameColumnFormat + "     %2$-11s     %3$-11s     %4$-11s%n",
                HEADER_ASSET, HEADER_PRICE, HEADER_SHARES, HEADER_VALUE));
        for (Asset asset : assets.getAssets()) {
            int index = assets.getAssets().indexOf(asset);
            String assetNameDisplay = String.format("%d - %s", index, asset.getName());
            output.append(
                    String.format(format,
                            assetNameDisplay, asset.getPricePerShare(), asset.getNumberOfShares(), asset.getAssetValue())
            );
        }

        return output.toString();
    }
}
