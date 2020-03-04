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
        int expectedEntries = MinorAssetClass.values().length;
        assertThat(assetMix.getMixItems().entrySet().size(), is(expectedEntries));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageTo50_getMixPercentageForReturns50() {
        MinorAssetClass assetClass = MinorAssetClass.values()[1];
        assetMix.updatePercentageFor(assetClass, 50d);
        assertThat(assetMix.getMixPercentageFor(assetClass), is(50d));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageLessThanZero_throwsNumberFormatException() {
        MinorAssetClass assetClass = MinorAssetClass.values()[1];
        double percentage = -0.1;
        Assertions.assertThrows(NumberFormatException.class,
                () -> assetMix.updatePercentageFor(assetClass, percentage));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageGreaterThan100_throwsNumberFormatException() {
        MinorAssetClass assetClass = MinorAssetClass.values()[1];
        double percentage = 100.1;
        Assertions.assertThrows(NumberFormatException.class,
                () -> assetMix.updatePercentageFor(assetClass, percentage));
    }

    @Test
    void getTotalMixPercentage() {
        MinorAssetClass assetClass1 = MinorAssetClass.values()[1];
        MinorAssetClass assetClass2 = MinorAssetClass.values()[0];
        assetMix.updatePercentageFor(assetClass1, 20.2);
        assetMix.updatePercentageFor(assetClass2, 40.6);
        assertThat(assetMix.getTotalMixPercentage(), is(60.8));
    }
}