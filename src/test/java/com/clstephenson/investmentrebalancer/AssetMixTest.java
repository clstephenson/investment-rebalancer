package com.clstephenson.investmentrebalancer;

import com.clstephenson.investmentrebalancer.commandrunner.InvalidAssetMixPercentageValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
        assetMix.updatePercentageFor(assetClass, BigDecimal.valueOf(50));
        assertThat(assetMix.getMixPercentageFor(assetClass).toPlainString(), is("50"));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageLessThanZero_throwsNumberFormatException() {
        AssetClass assetClass = AssetClass.values()[1];
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class,
                () -> assetMix.updatePercentageFor(assetClass, BigDecimal.valueOf(-1)));
    }

    @Test
    void givenEmptyAssetMix_whenUpdatePercentageGreaterThan100_throwsNumberFormatException() {
        AssetClass assetClass = AssetClass.values()[1];
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class,
                () -> assetMix.updatePercentageFor(assetClass, BigDecimal.valueOf(101)));
    }

    @Test
    void givenAssetMixOfTwoClasses_getTotalMixPercentage_returnsSumOfBothPercentages() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        AssetClass assetClass2 = AssetClass.values()[0];
        assetMix.updatePercentageFor(assetClass1, BigDecimal.valueOf(20));
        assetMix.updatePercentageFor(assetClass2, BigDecimal.valueOf(40));
        assertThat(assetMix.getTotalMixPercentage().toPlainString(), is("60"));
    }

    @Test
    void givenAssetMixWithTwoClassesOf50Percent_isValid_returnsTrue() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        AssetClass assetClass2 = AssetClass.values()[0];
        assetMix.getMixItems().put(assetClass1, BigDecimal.valueOf(50));
        assetMix.getMixItems().put(assetClass2, BigDecimal.valueOf(50));
        assertThat(assetMix.isValid(), is(true));
    }

    @Test
    void givenAssetMixWithTwoClassesOf70Percent_isValid_throwsException() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        AssetClass assetClass2 = AssetClass.values()[0];
        assetMix.getMixItems().put(assetClass1, BigDecimal.valueOf(70));
        assetMix.getMixItems().put(assetClass2, BigDecimal.valueOf(70));
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class, () -> assetMix.isValid());
    }

    @Test
    void givenAssetMixWithOneClassGreaterThan100Percent_isValid_throwsException() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        assetMix.getMixItems().put(assetClass1, BigDecimal.valueOf(101));
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class, () -> assetMix.isValid());
    }

    @Test
    void givenAssetMixWithOneClassLessThan0Percent_isValid_throwsException() throws InvalidAssetMixPercentageValue {
        AssetClass assetClass1 = AssetClass.values()[1];
        assetMix.getMixItems().put(assetClass1, BigDecimal.valueOf(-1));
        Assertions.assertThrows(InvalidAssetMixPercentageValue.class, () -> assetMix.isValid());
    }
}