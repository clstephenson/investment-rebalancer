package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.commandrunner.CommandBuilder;
import com.clstephenson.investmentrebalancer.context.Context;
import com.clstephenson.investmentrebalancer.context.ContextFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.math.BigDecimal;

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

    void createAssetsForTest(String ... names) {
        for (String name : names) {
            context.getAssets().getAssets().add(new Asset(name, BigDecimal.TEN));
        }
    }
}
