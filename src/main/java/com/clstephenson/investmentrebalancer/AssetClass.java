package com.clstephenson.investmentrebalancer;

public enum AssetClass {
    CASH ("Cash", AssetClassCategory.CASH),
    US_STOCKS ("US Stocks", AssetClassCategory.STOCKS),
    INTERNATIONAL_STOCKS ("Foreign Stocks", AssetClassCategory.STOCKS),
    US_BONDS ("US Bonds", AssetClassCategory.BONDS),
    INTERNATIONAL_BONDS ("Foreign Bonds", AssetClassCategory.BONDS)
    ;

    private final String name;
    private final AssetClassCategory assetCategory;

    AssetClass(String name, AssetClassCategory assetCategory) {
        this.name = name;
        this.assetCategory = assetCategory;
    }

    public String getName() {
        return name;
    }

    public AssetClassCategory getAssetCategory() {
        return assetCategory;
    }
}
