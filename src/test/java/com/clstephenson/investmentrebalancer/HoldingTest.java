package com.clstephenson.investmentrebalancer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class HoldingTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    Asset mockAsset;

    Holding holding;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        holding = new Holding(
                mockAsset,
                new BigDecimal("25")
        );
    }

    @AfterEach
    void tearDown() {
        holding = null;
    }

    @Test
    void givenAsset_when25SharesAtTwoDollars_expectValueEquals50() {
        when(mockAsset.getPricePerShare()).thenReturn(new BigDecimal(2.00));
        assertThat(holding.getValue(), Matchers.comparesEqualTo(new BigDecimal("50")));
    }

    @Test
    void givenAsset_whenZeroSharesAtTwoDollars_expectValueEqualsZero() {
        holding.setNumberOfShares(BigDecimal.ZERO);
        assertThat(holding.getValue(), Matchers.comparesEqualTo(new BigDecimal("0")));
    }
}