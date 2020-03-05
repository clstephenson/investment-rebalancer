package com.clstephenson.investmentrebalancer.commands;

import com.clstephenson.investmentrebalancer.Holdings;

public class BalanceAssets extends Command {

    @Override
    public String run(Holdings holdings, CommandOptions commandOptions)
            throws InvalidCommandArgsException {

        StringBuilder output = new StringBuilder();

//        if (assetClasses == null || assetClasses.isEmpty()) {
//            throw new InvalidCommandArgsException("No asset classes configured");
//        }
//
//        if (holdings == null) {
//            throw new InvalidCommandArgsException("ListAssets requires Holdings object to run.");
//        }
//
//        if (holdings.getHoldings().isEmpty()) {
//            output.append("There are no assets yet. Add an asset using the following command...\n");
//            output.append(ADD_ASSET.getSyntaxHelp());
//        } else {
//
//
//
//
//        }

        return output.toString();
    }
}
