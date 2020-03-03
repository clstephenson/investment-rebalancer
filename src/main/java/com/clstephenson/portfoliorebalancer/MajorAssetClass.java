package com.clstephenson.portfoliorebalancer;

public enum MajorAssetClass {
    STOCKS ("Stocks"),
    BONDS ("Bonds"),
    CASH ("Cash")
    ;

    private final String name;

    MajorAssetClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
