package com.clstephenson.portfoliorebalancer;

public enum MinorAssetClass {
    CASH ("Cash", MajorAssetClass.CASH),
    US_STOCKS ("US Stocks", MajorAssetClass.STOCKS),
    INTERNATIONAL_STOCKS ("Foreign Stocks", MajorAssetClass.STOCKS),
    US_BONDS ("US Bonds", MajorAssetClass.BONDS),
    INTERNATIONAL_BONDS ("Foreign Bonds", MajorAssetClass.BONDS)
    ;

    private final String name;
    private final MajorAssetClass parentAssetClass;

    MinorAssetClass(String name, MajorAssetClass parentAssetClass) {
        this.name = name;
        this.parentAssetClass = parentAssetClass;
    }

    public String getName() {
        return name;
    }

    public MajorAssetClass getParentAssetClass() {
        return parentAssetClass;
    }
}
