package com.clstephenson.investmentrebalancer;

import com.clstephenson.investmentrebalancer.commandrunner.InvalidAssetMixPercentageValue;
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
    void givenEmptyAssetMix_whenUpdatePercentageTo50_getMixPercentageForReturns50() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass = AssetClass.values()[1];
        assetMix.updatePercentageFor(assetClass, 50d);
        assertThat(assetMix.getMixPercentageFor(assetClass), is(50d));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageLessThanZero_throwsNumberFormatException() {
        AssetClass assetClass = AssetClass.values()[1];
        double percentage = -0.1;
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class,
                () -> assetMix.updatePercentageFor(assetClass, percentage));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageGreaterThan100_throwsNumberFormatException() {
        AssetClass assetClass = AssetClass.values()[1];
        double percentage = 100.1;
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class,
                () -> assetMix.updatePercentageFor(assetClass, percentage));
    }

    @Test
    void givenAssetMixOfTwoClasses_getTotalMixPercentage_returnsSumOfBothPercentages() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        AssetClass assetClass2 = AssetClass.values()[0];
        assetMix.updatePercentageFor(assetClass1, 20.2);
        assetMix.updatePercentageFor(assetClass2, 40.6);
        assertThat(assetMix.getTotalMixPercentage(), is(60.8));
    }

    @Test
    void givenAssetMixWithTwoClassesOf50Percent_isValid_returnsTrue() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        AssetClass assetClass2 = AssetClass.values()[0];
        assetMix.getMixItems().put(assetClass1, 50d);
        assetMix.getMixItems().put(assetClass2, 50d);
        assertThat(assetMix.isValid(), is(true));
    }

    @Test
    void givenAssetMixWithTwoClassesOf70Percent_isValid_throwsException() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        AssetClass assetClass2 = AssetClass.values()[0];
        assetMix.getMixItems().put(assetClass1, 70d);
        assetMix.getMixItems().put(assetClass2, 70d);
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class, () -> assetMix.isValid());
    }

    @Test
    void givenAssetMixWithOneClassGreaterThan100Percent_isValid_throwsException() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        assetMix.getMixItems().put(assetClass1, 101d);
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class, () -> assetMix.isValid());
    }

    @Test
    void givenAssetMixWithOneClassLessThan0Percent_isValid_throwsException() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        assetMix.getMixItems().put(assetClass1, -1d);
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class, () -> assetMix.isValid());
    }
}