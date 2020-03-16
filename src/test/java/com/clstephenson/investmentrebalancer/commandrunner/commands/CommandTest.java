package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.Holding;
import com.clstephenson.investmentrebalancer.commandrunner.CommandBuilder;
import com.clstephenson.investmentrebalancer.context.Context;
import com.clstephenson.investmentrebalancer.context.ContextFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CommandTest {

    @Mock
    Context context;

    @BeforeEach
    void setUp() {
        context = ContextFactory.getContext("dummyfile");
    }

    @AfterEach
    void tearDown() {
        context.getHoldings().getHoldings().clear();
        context.getAssets().getAssets().clear();
    }

    void runCommand(String input) throws Exception {
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
    }

    List<Asset> createAssetsForTest(String ... names) {
        ArrayList<Asset> assets = new ArrayList<>();
        for (String name : names) {
            Asset asset = new Asset(name, BigDecimal.TEN);
            assets.add(asset);
            context.getAssets().getAssets().add(asset);
        }
        return assets;
    }

    Holding createHoldingForTest(String assetName, BigDecimal shares) {
        return new Holding(createAssetsForTest(assetName).get(0), shares);
    }
}
