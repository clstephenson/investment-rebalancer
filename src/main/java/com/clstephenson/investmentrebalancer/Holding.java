package com.clstephenson.investmentrebalancer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

public class Holding {

    @JsonProperty
    private UUID uuid;
    private Asset asset;
    private BigDecimal numberOfShares;

    public Holding() {}

    public Holding(Asset asset, BigDecimal numberOfShares) {
        this.uuid = UUID.randomUUID();
        this.asset = asset;
        this.numberOfShares = numberOfShares;
    }

    public Asset getAsset() {
        return asset;
    }

    public BigDecimal getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(BigDecimal numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    @JsonIgnore
    private BigDecimal calculateValue(BigDecimal percentage) {
        return asset.getPricePerShare()
                .multiply(numberOfShares)
                .multiply(percentage)
                .divide(new BigDecimal("100"), RoundingMode.HALF_UP);
    }

    @JsonIgnore
    public BigDecimal getValue() {
        return calculateValue(BigDecimal.valueOf(100));
    }

    @JsonIgnore
    public BigDecimal getValue(AssetClass assetClass) {
        BigDecimal percentage = this.asset.getAssetMix().getMixPercentageFor(assetClass);
        return calculateValue(percentage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holding holding = (Holding) o;
        return uuid.equals(holding.uuid) &&
                Objects.equals(asset, holding.asset) &&
                numberOfShares.equals(holding.numberOfShares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, asset, numberOfShares);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder()
                .append(String.format("%s shares of %s at $%s per share\n", numberOfShares, asset.getName(), asset.getPricePerShare()))
                .append("ASSET MIX:\n")
                .append(asset.getAssetMix().toString());
        return stringBuilder.toString();
    }
}
