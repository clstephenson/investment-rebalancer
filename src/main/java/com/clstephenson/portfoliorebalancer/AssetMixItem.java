package com.clstephenson.portfoliorebalancer;

import java.util.Objects;

public class AssetMixItem {

    private MinorAssetClass minorAssetClass;
    private double percentage;

    public AssetMixItem(MinorAssetClass minorAssetClass, double percentage) {
        this.minorAssetClass = minorAssetClass;
        this.percentage = percentage;
    }

    public MinorAssetClass getMinorAssetClass() {
        return minorAssetClass;
    }

    public void setMinorAssetClass(MinorAssetClass minorAssetClass) {
        this.minorAssetClass = minorAssetClass;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetMixItem that = (AssetMixItem) o;
        return percentage == that.percentage &&
                minorAssetClass == that.minorAssetClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minorAssetClass, percentage);
    }

    @Override
    public String toString() {
        return "AssetMixItem{" +
                "assetClass=" + minorAssetClass +
                ", percentage=" + percentage +
                '}';
    }
}
