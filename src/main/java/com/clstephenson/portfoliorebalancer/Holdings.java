package com.clstephenson.portfoliorebalancer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Holdings {

    private List<Asset> assets;

    public Holdings() {
        assets = new ArrayList<>();
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public Optional<Asset> getAssetAtIndex(int index) {
        return index < 0 || index >= assets.size() ?
                Optional.empty() :
                Optional.of(assets.get(index));
    }

    public void add(Asset asset) {
        assets.add(asset);
    }

    public boolean deleteAsset(Asset asset) {
        return assets.remove(asset);
    }

    public BigDecimal getTotalValueOfAssets() {
        BigDecimal totalValue =  assets.stream().map(Asset::getAssetValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holdings holdings = (Holdings) o;
        return assets.equals(holdings.assets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assets);
    }

    @Override
    public String toString() {
        return "com.clstephenson.portfoliorebalancer.Holdings{" +
                "assets=" + assets +
                '}';
    }
}
