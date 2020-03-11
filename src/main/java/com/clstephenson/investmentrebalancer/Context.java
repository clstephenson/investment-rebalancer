package com.clstephenson.investmentrebalancer;

public class Context {
    private static volatile Context instance;
    private Holdings holdings;
    private TargetMix targetMix;

    static Context getContext() {
        if (instance != null) {
            return instance;
        }
        synchronized (Context.class) {
            if (instance == null) {
                instance = new Context();
            }
        }
        return instance;
    }

    private Context() {
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
