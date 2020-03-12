package com.clstephenson.investmentrebalancer;

import com.clstephenson.investmentrebalancer.commandrunner.InvalidAssetMixPercentageValue;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class Holdings {

    private List<Holding> holdings;

    public Holdings() {
        holdings = new ArrayList<>();
    }

    public List<Holding> getHoldings() {
        return holdings;
    }

    public Optional<Holding> getHoldingAtIndex(int index) {
        return index < 0 || index >= holdings.size() ?
                Optional.empty() :
                Optional.of(holdings.get(index));
    }

    public void add(Holding holding) {
        //todo: return optional of the added holding object
        holdings.add(holding);
    }

    public boolean deleteHolding(Holding holding) {
        return holdings.remove(holding);
    }

    @JsonIgnore
    public BigDecimal getTotalValueOfHoldings() {
        BigDecimal totalValue =
                holdings.stream()
                        .map(Holding::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalValue;
    }

    @JsonIgnore
    public Optional<Asset> getAssetFromHoldings(String assetName) {
        Asset foundAsset = this.getHoldings().stream()
                .map(Holding::getAsset)
                .filter(asset -> asset.getName().equalsIgnoreCase(assetName))
                .findFirst().orElse(null);
        return Optional.ofNullable(foundAsset);
    }

    @JsonIgnore
    public AssetMix getCumulativeAssetMix() throws InvalidAssetMixPercentageValue {
        AssetMix mix = new AssetMix();
        BigDecimal totalValue = getTotalValueOfHoldings();
        Map<AssetClass, BigDecimal> valuesByAssetClass = getCurrentValuations();

        for (AssetClass assetClass : AssetClass.values()) {
            BigDecimal percentage = valuesByAssetClass.get(assetClass)
                    .divide(totalValue, MathContext.DECIMAL128)
                    .multiply(new BigDecimal("100"));
            mix.updatePercentageFor(assetClass, percentage);
        }
        return mix;
    }

    @JsonIgnore
    public Map<AssetClass, BigDecimal> getCurrentValuations() {
        Map<AssetClass, BigDecimal> valuesByAssetClass = new HashMap<>();
        for (Holding holding : this.holdings) {
            Arrays.stream(AssetClass.values()).forEach(assetClass ->
                    valuesByAssetClass.merge(assetClass, holding.getValue(assetClass), BigDecimal::add)
            );
        }
        return valuesByAssetClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holdings holdings = (Holdings) o;
        return Objects.equals(this.holdings, holdings.getHoldings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(holdings);
    }

    @Override
    public String toString() {
        return "Holdings{" +
                "holdings=" + holdings +
                '}';
    }
}
