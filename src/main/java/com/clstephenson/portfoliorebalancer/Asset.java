package com.clstephenson.portfoliorebalancer;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Asset {

    private UUID uuid;
    private String name;
    private String symbol;
    private BigDecimal pricePerShare;
    private AssetMix assetMix;

    public Asset(String name, BigDecimal pricePerShare) {
        this(name, "", pricePerShare, new AssetMix());
    }

    public Asset(String name, String symbol, BigDecimal pricePerShare) {
        this(name, symbol, pricePerShare, new AssetMix());
    }

    public Asset(String name, String symbol, BigDecimal pricePerShare, AssetMix assetMix) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.symbol = symbol;
        this.pricePerShare = pricePerShare;
        this.assetMix = assetMix;
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

    public AssetMix getAssetMix() {
        return assetMix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return uuid.equals(asset.uuid) &&
                Objects.equals(name, asset.name) &&
                Objects.equals(symbol, asset.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, symbol);
    }

    @Override
    public String toString() {
        return "Asset{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", pricePerShare=" + pricePerShare +
                ", assetMix=" + assetMix +
                '}';
    }
}
