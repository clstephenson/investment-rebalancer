package com.clstephenson.investmentrebalancer.context;

import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.TargetMix;

public class Context {
    private Holdings holdings;
    private TargetMix targetMix;

    Context() {
        this.holdings = new Holdings();
        this.targetMix = new TargetMix();
    }

    public Holdings getHoldings() {
        return holdings;
    }

    public void setHoldings(Holdings holdings) {
        this.holdings = holdings;
    }

    public TargetMix getTargetMix() {
        return targetMix;
    }

    public void setTargetMix(TargetMix targetMix) {
        this.targetMix = targetMix;
    }
}
