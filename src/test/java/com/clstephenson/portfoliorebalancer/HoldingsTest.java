package com.clstephenson.portfoliorebalancer;

import com.clstephenson.portfoliorebalancer.Asset;
import com.clstephenson.portfoliorebalancer.Holdings;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

class HoldingsTest {

    Holdings holdings;
    Asset stock0;
    Asset stock1;

    @BeforeEach
    void setup() {
        holdings = new Holdings();
        stock0 = new Asset(
                "test asset 0",
                "ABCD",
                new BigDecimal("2.00"),
                new BigDecimal("25")
        );
        stock1 = new Asset(
                "test asset 1",
                "EFGH",
                new BigDecimal("1.50"),
                new BigDecimal("75.458")
        );
        holdings.add(stock0);
        holdings.add(stock1);
    }

    @AfterEach
    void tearDown() {
        holdings = null;
    }

    @Test
    void givenHoldings_whenAddAsset_theAssetIsFoundInHoldings() {
        Asset testAsset = new Asset("someStock", "SYMBOL", BigDecimal.ONE, BigDecimal.ONE);
        assertThat(holdings.getAssets(), Matchers.not(Matchers.hasItem(testAsset)));
        holdings.add(testAsset);
        assertThat(holdings.getAssets(), Matchers.hasItem(testAsset));
    }

    @Test
    void givenHoldings_whenTotalValueOfAssetsRequested_returnSumOfEachAssetValue() {
        BigDecimal actualValue = holdings.getTotalValueOfAssets();
        BigDecimal expectedValue = new BigDecimal("163.187");
        BigDecimal allowedError = new BigDecimal("0.005");
        assertThat(holdings.getTotalValueOfAssets(), Matchers.closeTo(expectedValue, allowedError));
    }

    @Test
    void givenHoldings_whenGetAssetAtIndex_correctAssetIsReturned() {
        assertThat(holdings.getAssetAtIndex(1).get(), Matchers.is(stock1));
    }

    @Test
    void givenHoldings_whenGetAssetAtOutOfBoundsIndex_returnsEmptyOptional() {
        int size = holdings.getAssets().size();
        assertThat(holdings.getAssetAtIndex(size), Matchers.is(Optional.empty()));
    }

    @Test
    void givenHoldings_whenRemoveAssetAtIndexZero_holdingContainsOnlyAsset1() {
        assertThat(holdings.getAssets(), Matchers.contains(stock0, stock1));
        holdings.deleteAsset(stock0);
        assertThat(holdings.getAssets(), Matchers.contains(stock1));
    }
}