package com.clstephenson.portfoliorebalancer;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class AssetMixTest {

    private AssetMix assetMix;

    @BeforeEach
    void setUp() {
        assetMix = new AssetMix();
    }

    @Test
    void whenNewAssetMix_getTotalMixPercentage_returnsZero() {
        assertThat(assetMix.getTotalMixPercentage(), is(0d));
    }

    @Test
    void whenAssetMix_getTotalMixPercentage_returnsSumOfClassPercentages() {
        assetMix.updatePercentageFor(MinorAssetClass.CASH, 50);
        assetMix.updatePercentageFor(MinorAssetClass.US_BONDS, 50);
        assertThat(assetMix.getTotalMixPercentage(), is(100d));
    }

    @Test
    void whenUpdatePercentage_getMixPercentage_returnsUpdatedValue() {
        MinorAssetClass assetClassToTest = MinorAssetClass.CASH;
        double percentage = 50d;
        assetMix.updatePercentageFor(assetClassToTest, percentage);
        assertThat(assetMix.getMixPercentageFor(assetClassToTest), is(percentage));
    }

    @Test
    void whenUpdatePercentageLessThanZero_throwsNumberFormatException() {
        MinorAssetClass assetClassToTest = MinorAssetClass.CASH;
        double percentage = -0.1;
        Assertions.assertThrows(NumberFormatException.class,
                () -> assetMix.updatePercentageFor(assetClassToTest, percentage));
    }

    @Test
    void whenUpdatePercentageGreaterThan100_throwsNumberFormatException() {
        MinorAssetClass assetClassToTest = MinorAssetClass.CASH;
        double percentage = 100.1;
        Assertions.assertThrows(NumberFormatException.class,
                () -> assetMix.updatePercentageFor(assetClassToTest, percentage));
    }
}