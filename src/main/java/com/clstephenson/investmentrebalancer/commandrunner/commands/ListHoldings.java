package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.commandrunner.CommandOptions;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;

import static com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands.ADD_ASSET;

public class ListHoldings extends Command {

    @Override
    public String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException {

        StringBuilder output = new StringBuilder();

        if (holdings == null) {
            throw new InvalidCommandArgsException("ListHoldings requires Holdings object to run.");
        }

        if (holdings.getHoldings().isEmpty()) {
            output.append("There are no holdings yet. Use the following command to add one...\n");
            output.append(ADD_ASSET.getSyntaxHelp());
        } else {
            Holdings matchedHoldings = new Holdings();
            if (commandOptions != null) {
                commandOptions.getOptionValue("n")
                        .ifPresent(s -> {
                            holdings.getHoldings().stream()
                                    .filter(holding -> holding.getAsset().getName().equalsIgnoreCase(s))
                                    .forEach(matchedHoldings::add);
                        });
            }

            if (matchedHoldings.getHoldings().isEmpty()) {
                output.append(buildOutputString(holdings));
            } else {
                output.append(buildOutputString(matchedHoldings));
            }
        }

        return output.toString();
    }

    private String buildOutputString (Holdings holdings) {
        StringBuilder output = new StringBuilder();

        final String HEADER_ASSET = "Asset";
        final String HEADER_PRICE = "Share Price ($)";
        final String HEADER_SHARES = "Qty Shares";
        final String HEADER_VALUE = "Value ($)";

        int maxNameLength = holdings.getHoldings().stream()
                .mapToInt(value -> value.getAsset().getName().length()).max().getAsInt();
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
        for (Holding holding : holdings.getHoldings()) {
            int index = holdings.getHoldings().indexOf(holding);
            String assetNameDisplay = String.format("%d - %s", index, holding.getAsset().getName());
            output.append(
                    String.format(format,
                            assetNameDisplay, holding.getAsset().getPricePerShare(), holding.getNumberOfShares(), holding.getValue())
            );
        }

        return output.toString();
    }
}
