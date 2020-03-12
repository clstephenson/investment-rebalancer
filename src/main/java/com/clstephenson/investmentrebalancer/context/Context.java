package com.clstephenson.investmentrebalancer.context;

import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.TargetMix;

import java.io.File;

public class Context {
    private File dataFile;
    private Holdings holdings;
    private TargetMix targetMix;

    Context() {
        this.dataFile = null;
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

    public File getDataFile() {
        return dataFile;
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }
}
