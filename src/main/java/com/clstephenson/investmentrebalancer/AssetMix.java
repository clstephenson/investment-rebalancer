package com.clstephenson.investmentrebalancer;

import com.clstephenson.investmentrebalancer.commandrunner.InvalidAssetMixPercentageValue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class AssetMix {

    private final Map<AssetClass, Double> mixItems;

    public AssetMix() {
        this.mixItems = Arrays.stream(AssetClass.values())
                .collect(
                        Collectors.toMap(assetClass -> assetClass, assetClass -> 0d)
                );
    }

    public Map<AssetClass, Double> getMixItems() {
        return mixItems;
    }

    public double getMixPercentageFor(AssetClass assetClass) {
        return this.mixItems.get(assetClass);
    }

    public void updatePercentageFor(AssetClass assetClass, double percentage) throws InvalidAssetMixPercentageValue {
        if (percentage < 0 || percentage > 100) {
            throw new InvalidAssetMixPercentageValue("Percentage must be between 0 and 100.");
        }
        this.mixItems.replace(assetClass, percentage);
    }

    public double getTotalMixPercentage() {
        return this.mixItems.values().stream()
                .reduce(0d, Double::sum);
    }

    public boolean isValid() throws InvalidAssetMixPercentageValue {
        boolean isValid = true;
        for (Map.Entry<AssetClass, Double> entry : mixItems.entrySet()) {
            if (entry.getValue() < 0.0 || entry.getValue() > 100.0) {
                isValid = false;
                break;
            }
        }
        if (getTotalMixPercentage() != 100.0) {
            throw new InvalidAssetMixPercentageValue("Total mix percentage must equal 100%");
        } else if (!isValid) {
            throw new InvalidAssetMixPercentageValue("Percentage must be between 0 and 100%");
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        mixItems.entrySet().stream()
                .sorted(Comparator.comparing(t -> t.getKey().ordinal()))
                .forEachOrdered(entry ->
                        stringBuilder.append(String.format("\t%s: %s percent\n",
                                entry.getKey().getName(),
                                entry.getValue())
                        )
                );
        return stringBuilder.toString();
    }
}
