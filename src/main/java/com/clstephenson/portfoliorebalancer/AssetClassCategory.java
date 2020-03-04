package com.clstephenson.portfoliorebalancer;

public enum AssetClassCategory {
    STOCKS ("Stocks"),
    BONDS ("Bonds"),
    CASH ("Cash")
    ;

    private final String name;

    AssetClassCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
