package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.Validations;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidCommandArgsException;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;

import java.math.BigDecimal;
import java.util.Optional;

public class AddAsset extends Command {

    @Override
    public String run()
            throws InvalidCommandArgsException, InvalidOptionsException {

        String syntax = AvailableCommands.ADD_ASSET.getSyntaxHelp();

        if (getCommandOptions() == null) {
            throw new InvalidOptionsException("Options are required", syntax);
        }

        String assetName = getCommandOptions().getOptionValue("n")
                .orElseThrow(() -> new InvalidOptionsException("asset name missing", syntax));
        String sharePrice = getCommandOptions().getOptionValue("p")
                .orElseThrow(() -> new InvalidOptionsException("share price missing", syntax));
        String numberOfShares = getCommandOptions().getOptionValue("s")
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

        Optional<Asset> optionalAsset = getHoldings().getAssetFromHoldings(assetName);
        Asset asset = optionalAsset.orElseGet(
                () -> new Asset(assetName, new BigDecimal(sharePrice))
        );
        asset.setPricePerShare(new BigDecimal(sharePrice));
        Holding newHolding = new Holding(asset, new BigDecimal(numberOfShares));
        getHoldings().add(newHolding);
        return String.format("%s\nNote: Existing holdings with asset=%s were updated to reflect a price of %s per share.",
                newHolding.toString(), asset.getName(), asset.getPricePerShare().toString());
    }
}