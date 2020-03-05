package com.clstephenson.investmentrebalancer.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.Validations;

import java.math.BigDecimal;

public class AddAsset extends Command {

    @Override
    public String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException, InvalidOptionsException {

        String syntax = AvailableCommands.ADD_ASSET.getSyntaxHelp();

        if (commandOptions == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        String assetName = commandOptions.getOptionValue("n")
                .orElseThrow(() -> new InvalidOptionsException("asset name missing", syntax));
        String sharePrice = commandOptions.getOptionValue("p")
                .orElseThrow(() -> new InvalidOptionsException("share price missing", syntax));
        String numberOfShares = commandOptions.getOptionValue("s")
                .orElseThrow(() -> new InvalidOptionsException("number of shares missing", syntax));

        boolean isValid = true;
        if (assetName == null || assetName.isEmpty()) {
            isValid = false;
        } else if (sharePrice == null || !Validations.StringIsDoubleValue(sharePrice)) {
            isValid = false;
        } else if (numberOfShares == null || !Validations.StringIsDoubleValue(numberOfShares)) {
            isValid = false;
        }

        if (!isValid) {
            String message = "Invalid command options.";
            throw new InvalidOptionsException(message, syntax);
        }

        Asset asset = new Asset(assetName, new BigDecimal(sharePrice));
        Holding newHolding = new Holding(asset, new BigDecimal(numberOfShares));
        holdings.add(newHolding);
        return newHolding.toString();
    }
}
