package com.clstephenson.investmentrebalancer;

import com.clstephenson.investmentrebalancer.commandrunner.InvalidAssetMixPercentageValue;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class HoldingsTest {

    Holdings holdings;
    @Mock Holding holding1;
    @Mock Holding holding2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        when(holding1.getValue()).thenReturn(new BigDecimal(50));
        when(holding2.getValue()).thenReturn(new BigDecimal(113.187));

        holdings = new Holdings();
        holdings.add(holding1);
        holdings.add(holding2);
    }

    @AfterEach
    void tearDown() {
        holdings = null;
    }

    @Test
    void givenHoldings_whenAddAsset_theAssetIsFoundInHoldings() {
        Holding testHolding = new Holding(mock(Asset.class), BigDecimal.ONE);
        assertThat(holdings.getHoldings(), Matchers.not(Matchers.hasItem(testHolding)));
        holdings.add(testHolding);
        assertThat(holdings.getHoldings(), Matchers.hasItem(testHolding));
    }

    @Test
    void givenHoldings_whenTotalValueOfAssetsRequested_returnSumOfEachAssetValue() {
        BigDecimal expectedValue = new BigDecimal("163.187");
        BigDecimal allowedError = new BigDecimal("0.005");
        assertThat(holdings.getTotalValueOfHoldings(), Matchers.closeTo(expectedValue, allowedError));
    }

    @Test
    void givenHoldings_whenGetAssetAtIndex_correctAssetIsReturned() {
        assertThat(holdings.getHoldingAtIndex(1).get(), Matchers.is(holding2));
    }

    @Test
    void givenHoldings_whenGetAssetAtOutOfBoundsIndex_returnsEmptyOptional() {
        int size = holdings.getHoldings().size();
        assertThat(holdings.getHoldingAtIndex(size), Matchers.is(Optional.empty()));
    }

    @Test
    void givenHoldings_whenRemoveAssetAtIndexZero_holdingContainsOnlyAsset1() {
        assertThat(holdings.getHoldings(), Matchers.contains(holding1, holding2));
        holdings.deleteHolding(holding1);
        assertThat(holdings.getHoldings(), Matchers.contains(holding2));
    }

    @Test
    void givenHoldingsWithTwoAssets_whenGetAssetFromHoldings_correctAssetIsReturned() {
        Holdings testHoldings = new Holdings();
        Asset asset1 = new Asset("stock", "", BigDecimal.TEN, Mockito.mock(AssetMix.class));
        Asset asset2 = new Asset("stock2", "", BigDecimal.ONE, Mockito.mock(AssetMix.class));
        testHoldings.add(new Holding(asset1, BigDecimal.ONE));
        testHoldings.add(new Holding(asset2, BigDecimal.ONE));
        assertThat(testHoldings.getAssetFromHoldings("stock").get(), is(asset1));
    }

    @Test
    void givenHoldingsWithTwoAssets_whenGetCumulativeAssetMix_returnAssetMixWithCorrectCalculation() throws InvalidAssetMixPercentageValue {
        Holdings testHoldings = new Holdings();
        AssetMix mix1 = new AssetMix();
        AssetMix mix2 = new AssetMix();
        mix1.updatePercentageFor(AssetClass.CASH, new BigDecimal("25"));
        mix1.updatePercentageFor(AssetClass.US_STOCKS, new BigDecimal("75"));
        mix2.updatePercentageFor(AssetClass.CASH, new BigDecimal("15"));
        mix2.updatePercentageFor(AssetClass.US_BONDS, new BigDecimal("85"));
        Asset asset1 = new Asset("stock", "", BigDecimal.TEN, mix1);
        Asset asset2 = new Asset("stock2", "", BigDecimal.TEN, mix2);
        testHoldings.add(new Holding(asset1, new BigDecimal(100)));
        testHoldings.add(new Holding(asset2, new BigDecimal(100)));

        AssetMix expectedMix = new AssetMix();
        expectedMix.updatePercentageFor(AssetClass.CASH, new BigDecimal("20"));
        expectedMix.updatePercentageFor(AssetClass.US_STOCKS, new BigDecimal("37.5"));
        expectedMix.updatePercentageFor(AssetClass.US_BONDS, new BigDecimal("42.5"));
        assertThat(testHoldings.getCumulativeAssetMix(), is(expectedMix));
    }
}