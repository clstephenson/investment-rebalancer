package com.clstephenson.portfoliorebalancer;

import com.clstephenson.portfoliorebalancer.Asset;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;


class AssetTest {

    Asset asset;

    @BeforeEach
    void setUp() {
        asset = new Asset(
                "test asset",
                "ABCD",
                new BigDecimal("2.00"),
                new BigDecimal("25")
        );
    }

    @AfterEach
    void tearDown() {
        asset = null;
    }

    @Test
    void givenAsset_whenTwoSharesAtTwoDollars_expectValueEquals50() {
        assertThat(asset.getAssetValue(), Matchers.comparesEqualTo(new BigDecimal("50")));
    }

    @Test
    void givenAsset_whenZeroSharesAtTwoDollars_expectValueEqualsZero() {
        asset.setNumberOfShares(BigDecimal.ZERO);
        assertThat(asset.getAssetValue(), Matchers.comparesEqualTo(new BigDecimal("0")));
    }
}