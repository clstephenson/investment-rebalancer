package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.Validations;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

import java.math.BigDecimal;

public class AddHolding extends Command {

    @Override
    public String run()
            throws InvalidOptionsException {

        String syntax = getContext().getStringResource(getCommandType().getSyntaxKey());

        if (getCommandOptions() == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        String assetName = getCommandOptions().getOptionValue("n")
                .orElseThrow(() -> new InvalidOptionsException("asset name missing", syntax));
        String numberOfShares = getCommandOptions().getOptionValue("s")
                .orElseThrow(() -> new InvalidOptionsException("number of shares missing", syntax));

        boolean isValid = true;
        if (assetName == null || assetName.isEmpty()) {
            isValid = false;
        } else if (numberOfShares == null || !Validations.StringIsDoubleValue(numberOfShares)) {
            isValid = false;
        }

        if (!isValid) {
            String message = "Invalid command options.";
            throw new InvalidOptionsException(message, syntax);
        }

        Asset asset = getContext().getAssets()
                .getAssetMatching(a -> a.getName().equalsIgnoreCase(assetName))
                .orElseThrow(() -> new InvalidOptionsException("The specified asset was not found."));

        Holding newHolding = new Holding(asset, new BigDecimal(numberOfShares));
        getContext().getHoldings().add(newHolding);
        return "The holding was added";
    }
}
