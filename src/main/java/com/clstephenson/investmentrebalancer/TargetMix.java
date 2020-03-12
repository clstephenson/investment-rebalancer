package com.clstephenson.investmentrebalancer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TargetMix {

    private AssetMix assetMix;

    public TargetMix() {
        this.assetMix = new AssetMix();
    }

    public AssetMix getAssetMix() {
        return assetMix;
    }

    public void setAssetMix(AssetMix assetMix) {
        this.assetMix = assetMix;
    }

    public Map<AssetClass, BigDecimal> getTargetValuations(Holdings holdings) {
        //todo need to write test for this
        Map<AssetClass, BigDecimal> valuations = new HashMap<>();
        BigDecimal totalValueOfHoldings = holdings.getTotalValueOfHoldings();
        for (AssetClass assetClass : AssetClass.values()) {
            BigDecimal targetValue = totalValueOfHoldings
                    .multiply(this.assetMix.getMixPercentageFor(assetClass))
                    .movePointLeft(2);
            valuations.put(assetClass, targetValue);
        }
        return valuations;
    }
}
