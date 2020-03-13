package com.clstephenson.investmentrebalancer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Assets {

    private List<Asset> assets;

    public Assets() {
        assets = new ArrayList<>();
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public Optional<Asset> getAssetMatching(Predicate<Asset> filter) {
        return assets.stream().filter(filter).findFirst();
    }

    public Optional<Asset> getAssetAtIndex(int index) {
        return index < 0 || index >= assets.size() ?
                Optional.empty() :
                Optional.of(assets.get(index));
    }

    public void add(Asset asset) {
        //todo: return optional of the added asset object
        assets.add(asset);
    }

    public boolean deleteAsset(Asset asset) {
        return assets.remove(asset);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return assets.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assets assets = (Assets) o;
        return Objects.equals(this.assets, assets.getAssets());
    }

    @Override
    public int hashCode() {
        return Objects.hash(assets);
    }

    @Override
    public String toString() {
        return "Assets{" +
                "assets=" + assets +
                '}';
    }

    public Stream<Asset> stream() {
        return assets.stream();
    }
}
