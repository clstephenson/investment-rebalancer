package com.clstephenson.portfoliorebalancer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class AssetMixTest {

    AssetMix assetMix;

    @BeforeEach
    void setUp() {
        assetMix = new AssetMix();
    }

    @Test
    void newAssetMix_containsMapWithNumberOfEntriesMatchingMinorAssetClassValues() {
        int expectedEntries = AssetClass.values().length;
        assertThat(assetMix.getMixItems().entrySet().size(), is(expectedEntries));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageTo50_getMixPercentageForReturns50() {
        AssetClass assetClass = AssetClass.values()[1];
        assetMix.updatePercentageFor(assetClass, 50d);
        assertThat(assetMix.getMixPercentageFor(assetClass), is(50d));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageLessThanZero_throwsNumberFormatException() {
        AssetClass assetClass = AssetClass.values()[1];
        double percentage = -0.1;
        Assertions.assertThrows(NumberFormatException.class,
                () -> assetMix.updatePercentageFor(assetClass, percentage));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageGreaterThan100_throwsNumberFormatException() {
        AssetClass assetClass = AssetClass.values()[1];
        double percentage = 100.1;
        Assertions.assertThrows(NumberFormatException.class,
                () -> assetMix.updatePercentageFor(assetClass, percentage));
    }

    @Test
    void getTotalMixPercentage() {
        AssetClass assetClass1 = AssetClass.values()[1];
        AssetClass assetClass2 = AssetClass.values()[0];
        assetMix.updatePercentageFor(assetClass1, 20.2);
        assetMix.updatePercentageFor(assetClass2, 40.6);
        assertThat(assetMix.getTotalMixPercentage(), is(60.8));
    }
}