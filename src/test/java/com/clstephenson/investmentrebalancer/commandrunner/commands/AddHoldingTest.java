package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.CommandBuilder;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
class AddHoldingTest extends CommandTest {

    @Test
    void givenAssets_whenAddHolding_oneHoldingIsFound() throws Exception {
        String cmd = AvailableCommands.ADD_HOLDING.getCommandKey();
        createAssetsForTest("stock1", "stock2");
        String input = String.format("%s -n %s -s %s", cmd, "stock1", BigDecimal.TEN);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getHoldings().getHoldings().size(), is(1));
    }

    @Test
    void givenAssets_whenAddHolding_holdingContainsAssetWithCorrectName() throws Exception {
        String cmd = AvailableCommands.ADD_HOLDING.getCommandKey();
        createAssetsForTest("stock1", "stock2");
        String input = String.format("%s -n %s -s %s", cmd, "stock1", BigDecimal.TEN);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getHoldings().getHoldings().get(0).getAsset().getName(), is("stock1"));
    }

    @Test
    void givenEmptyAssetName_whenAddHolding_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.ADD_HOLDING.getCommandKey();
        String input = String.format("%s -n -s %s", cmd, BigDecimal.TEN);
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void givenAssetNameOptionMissing_whenAddHolding_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.ADD_HOLDING.getCommandKey();
        String input = String.format("%s -s %s", cmd, BigDecimal.TEN);
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void givenEmptyShares_whenAddHolding_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.ADD_HOLDING.getCommandKey();
        String input = String.format("%s -n stockname -s", cmd);
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void givenSharesOptionMissing_whenAddHolding_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.ADD_HOLDING.getCommandKey();
        String input = String.format("%s -n stockname", cmd);
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

}