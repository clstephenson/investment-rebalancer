package com.clstephenson.investmentrebalancer;

import com.clstephenson.investmentrebalancer.commandrunner.InvalidAssetMixPercentageValue;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class AssetMix {

    private final Map<AssetClass, BigDecimal> mixItems;

    public AssetMix() {
        this.mixItems = Arrays.stream(AssetClass.values())
                .collect(
                        Collectors.toMap(assetClass -> assetClass, assetClass -> BigDecimal.ZERO)
                );
    }

    public Map<AssetClass, BigDecimal> getMixItems() {
        return mixItems;
    }

    public BigDecimal getMixPercentageFor(AssetClass assetClass) {
        return this.mixItems.get(assetClass);
    }

    public void updatePercentageFor(AssetClass assetClass, BigDecimal percentage) throws InvalidAssetMixPercentageValue {
        if (percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new InvalidAssetMixPercentageValue("Percentage must be between 0 and 100.");
        }
        this.mixItems.replace(assetClass, percentage);
    }

    @JsonIgnore
    public BigDecimal getTotalMixPercentage() {
        return this.mixItems.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @JsonIgnore
    public boolean isValid() throws InvalidAssetMixPercentageValue {
        boolean isValid = true;
        for (Map.Entry<AssetClass, BigDecimal> entry : mixItems.entrySet()) {
            if (entry.getValue().compareTo(BigDecimal.ZERO) < 0 || entry.getValue().compareTo(BigDecimal.valueOf(100)) > 0) {
                isValid = false;
                break;
            }
        }
        if (getTotalMixPercentage().compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new InvalidAssetMixPercentageValue("Total mix percentage must equal 100%");
        } else if (!isValid) {
            throw new InvalidAssetMixPercentageValue("Percentage must be between 0 and 100%");
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetMix mix = (AssetMix) o;
        if (this.mixItems.size() != mix.mixItems.size()) return false;
        for (AssetClass assetClass : this.mixItems.keySet()) {
            if (!mix.mixItems.containsKey(assetClass) ||
                    (this.mixItems.get(assetClass).compareTo(mix.mixItems.get(assetClass)) != 0)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mixItems);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        mixItems.entrySet().stream()
                .sorted(Comparator.comparing(t -> t.getKey().ordinal()))
                .forEachOrdered(entry ->
                        stringBuilder.append(String.format("\t%s: %s percent\n",
                                entry.getKey().getName(),
                                entry.getValue().setScale(2, RoundingMode.HALF_UP))
                        )
                );
        return stringBuilder.toString();
    }
}
