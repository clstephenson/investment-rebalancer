package com.clstephenson.portfoliorebalancer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class Asset {

    private UUID uuid;
    private String name;
    private String symbol;
    private BigDecimal pricePerShare;
    private BigDecimal numberOfShares;
    private AssetMix assetMix;

    public Asset(String name, BigDecimal pricePerShare, BigDecimal numberOfShares) {
        this(name, "", pricePerShare, numberOfShares, new AssetMix());
    }

    public Asset(String name, String symbol, BigDecimal pricePerShare, BigDecimal numberOfShares) {
        this(name, symbol, pricePerShare, numberOfShares, new AssetMix());
    }

    public Asset(String name, String symbol, BigDecimal pricePerShare, BigDecimal numberOfShares,
                 AssetMix assetMix) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.symbol = symbol;
        this.pricePerShare = pricePerShare;
        this.numberOfShares = numberOfShares;
        this.assetMix = assetMix;
    }

    public BigDecimal getAssetValue() {
        return pricePerShare.multiply(numberOfShares).setScale(2, RoundingMode.HALF_UP);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(BigDecimal pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public BigDecimal getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(BigDecimal numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public AssetMix getAssetMix() {
        return assetMix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return uuid.equals(asset.uuid) &&
                name.equals(asset.name) &&
                Objects.equals(symbol, asset.symbol) &&
                pricePerShare.equals(asset.pricePerShare) &&
                numberOfShares.equals(asset.numberOfShares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, symbol, pricePerShare, numberOfShares);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder()
                .append(String.format("%s shares of %s at $%s per share\n", numberOfShares, name, pricePerShare))
                .append("ASSET MIX:\n")
                .append(assetMix.toString());
        return stringBuilder.toString();
    }
}
