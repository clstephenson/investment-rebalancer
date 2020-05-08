package com.clstephenson.investmentrebalancer.context;

import com.clstephenson.investmentrebalancer.Assets;
import com.clstephenson.investmentrebalancer.Holdings;
import com.clstephenson.investmentrebalancer.TargetMix;

import java.io.File;
import java.util.ResourceBundle;
import java.util.Set;

public class Context {
    private File dataFile;
    private Holdings holdings;
    private Assets assets;
    private TargetMix targetMix;
    private ResourceBundle resourceBundle;

    Context() {
        this.dataFile = null;
        this.holdings = new Holdings();
        this.assets = new Assets();
        this.targetMix = new TargetMix();
        this.resourceBundle = ResourceBundle.getBundle("strings");
    }

    public Holdings getHoldings() {
        return holdings;
    }

    public void setHoldings(Holdings holdings) {
        this.holdings = holdings;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
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

    public String getStringResource(String key) {
        if (resourceBundle.containsKey(key)) {
            return resourceBundle.getString(key);
        } else {
            throw new RuntimeException("Key " + key + " not found in resource bundle.");
        }
    }

    public Set<String> getResourceKeys() {
        return resourceBundle.keySet();
    }
}
