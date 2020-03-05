package com.clstephenson.investmentrebalancer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public BigDecimal getTotalValueOfHoldings() {
        BigDecimal totalValue =
                holdings.stream()
                        .map(Holding::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalValue;
    }

    public Optional<Asset> getAssetFromHoldings(String assetName) {
        Asset foundAsset = this.getHoldings().stream()
                .map(Holding::getAsset)
                .filter(asset -> asset.getName().equalsIgnoreCase(assetName))
                .findFirst().orElse(null);
        return Optional.ofNullable(foundAsset);
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
