package com.clstephenson.portfoliorebalancer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Holdings {

    private List<Holding> holdings;

    public Holdings() {
        holdings = new ArrayList<>();
    }

    public List<Holding> getHoldings() {
        return holdings;
    }

    public Optional<Holding> getHoldingAtIndex(int index) {
        return index < 0 || index >= holdings.size() ?
                Optional.empty() :
                Optional.of(holdings.get(index));
    }

    public void add(Holding holding) {
        //todo: return optional of the added holding object
        holdings.add(holding);
    }

    public boolean deleteHolding(Holding holding) {
        return holdings.remove(holding);
    }

    public BigDecimal getTotalValueOfHoldings() {
        BigDecimal totalValue =
                holdings.stream()
                        .map(Holding::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holdings holdings = (Holdings) o;
        return holdings.equals(holdings.holdings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(holdings);
    }

    @Override
    public String toString() {
        return "Holdings{" +
                "holdings=" + holdings +
                '}';
    }
}
