package com.clstephenson.investmentrebalancer;

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
}
