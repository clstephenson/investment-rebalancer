package com.clstephenson.investmentrebalancer.commandrunner.commands;

public class ShowTargetAssetMix extends Command {

    @Override
    public String run() {

        return getContext().getTargetMix().getAssetMix().toString();

    }

}
