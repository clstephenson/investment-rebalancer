package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Holding;
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
class UpdateHoldingTest extends CommandTest {

    @Test
    void givenHoldingsWithOneHolding_whenUpdateHolding_holdingsContainsOneHolding() throws Exception {
        String cmd = AvailableCommands.UPDATE_HOLDING.getCommandKey();
        Holding holding = createHoldingForTest("stock1", BigDecimal.TEN);
        context.getHoldings().add(holding);
        String input = String.format("%s -i %s -s %s", getStringResource(cmd), "0", BigDecimal.ONE);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getHoldings().getHoldings().size(), is(1));
    }

    @Test
    void givenHoldings_whenUpdateHolding_holdingContainsAssetWithCorrectShares() throws Exception {
        String cmd = AvailableCommands.UPDATE_HOLDING.getCommandKey();
        Holding holding = createHoldingForTest("stock1", BigDecimal.TEN);
        context.getHoldings().add(holding);
        String input = String.format("%s -i %s -s %s", getStringResource(cmd), "0", BigDecimal.ONE);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getHoldings().getHoldings().get(0).getNumberOfShares(), is(BigDecimal.ONE));
    }

    @Test
    void givenOutOfRangeIndex_whenAddHolding_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.UPDATE_HOLDING.getCommandKey();
        Holding holding = createHoldingForTest("stock1", BigDecimal.TEN);
        context.getHoldings().add(holding);
        String input = String.format("%s -i %s -s %s", getStringResource(cmd), "2", BigDecimal.ONE);
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void givenEmptyIndex_whenAddHolding_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.UPDATE_HOLDING.getCommandKey();
        Holding holding = createHoldingForTest("stock1", BigDecimal.TEN);
        context.getHoldings().add(holding);
        String input = String.format("%s -i -s %s", getStringResource(cmd), BigDecimal.ONE);
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void givenIndexOptionMissing_whenAddHolding_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.UPDATE_HOLDING.getCommandKey();
        Holding holding = createHoldingForTest("stock1", BigDecimal.TEN);
        context.getHoldings().add(holding);
        String input = String.format("%s -s %s", getStringResource(cmd), BigDecimal.ONE);
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void givenEmptyShares_whenAddHolding_sharesDoesNotChange() throws Exception {
        String cmd = AvailableCommands.UPDATE_HOLDING.getCommandKey();
        Holding holding = createHoldingForTest("stock1", BigDecimal.TEN);
        context.getHoldings().add(holding);
        String input = String.format("%s -i %s -s", getStringResource(cmd), "0");
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getHoldings().getHoldings().get(0).getNumberOfShares(), is(BigDecimal.TEN));
    }

    @Test
    void givenSharesOptionMissing_whenAddHolding_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.UPDATE_HOLDING.getCommandKey();
        Holding holding = createHoldingForTest("stock1", BigDecimal.TEN);
        context.getHoldings().add(holding);
        String input = String.format("%s -i %s", getStringResource(cmd), "0");
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getHoldings().getHoldings().get(0).getNumberOfShares(), is(BigDecimal.TEN));
    }

}