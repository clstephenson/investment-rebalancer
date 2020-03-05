package com.clstephenson.investmentrebalancer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

public class Holding {

    private UUID uuid;
    private Asset asset;
    private BigDecimal numberOfShares;

    public Holding(Asset asset, BigDecimal numberOfShares) {
        this.uuid = UUID.randomUUID();
        this.asset = asset;
        this.numberOfShares = numberOfShares;
    }

    public Asset getAsset() {
        return asset;
    }

    public BigDecimal getValue() {
        return asset.getPricePerShare()
                .multiply(numberOfShares)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(BigDecimal numberOfShares) {
        this.numberOfShares = numberOfShares;
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
