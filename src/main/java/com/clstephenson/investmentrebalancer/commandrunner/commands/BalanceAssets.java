package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.AssetClass;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class BalanceAssets extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException {

        StringBuilder output = new StringBuilder();

        if (getContext().getHoldings() == null) {
            throw new InvalidCommandArgsException("balance requires Holdings object to run.");
        }

        if (getContext().getHoldings().isEmpty()) {
            output.append("There are no holdings yet. Add a holding using the following command...\n");
            output.append(getContext().getStringResource(AvailableCommands.ADD_HOLDING.getSyntaxKey()));
        } else {
            Map<AssetClass, BigDecimal> currentValuations = getContext().getHoldings().getCurrentValuations();
            Map<AssetClass, BigDecimal> targetValuations = getContext().getTargetMix().getTargetValuations(getContext().getHoldings());

            final String HEADER_CLASS = "Asset Class";
            final String HEADER_TARGET = "Target Value";
            final String HEADER_ACTUAL = "Actual Value";
            final String HEADER_CHANGE = "Suggested Change";
            output.append(String.format("%1$-15s %2$17s %3$17s %4$17s\n", HEADER_CLASS, HEADER_TARGET, HEADER_ACTUAL, HEADER_CHANGE));
            for (AssetClass assetClass : AssetClass.values()) {
                BigDecimal valuationDelta = targetValuations.get(assetClass).subtract(currentValuations.get(assetClass));
                String row = String.format("%1$-15s %2$,(17.0f %3$,(17.0f %4$,(17.0f\n",
                        assetClass.getName(),
                        targetValuations.get(assetClass).setScale(0, RoundingMode.HALF_UP),
                        currentValuations.get(assetClass).setScale(0, RoundingMode.HALF_UP),
                        valuationDelta.setScale(0, RoundingMode.HALF_UP));

                output.append(row);
            }
        }
        return output.toString();
    }
}
