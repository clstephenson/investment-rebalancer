package com.clstephenson.investmentrebalancer.commandrunner.commands;

import com.clstephenson.investmentrebalancer.Asset;
import com.clstephenson.investmentrebalancer.commandrunner.AvailableCommands;
import com.clstephenson.investmentrebalancer.commandrunner.CommandBuilder;
import com.clstephenson.investmentrebalancer.commandrunner.InvalidOptionsException;
import com.clstephenson.investmentrebalancer.context.Context;
import com.clstephenson.investmentrebalancer.context.ContextFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
class UpdateAssetTest {

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

    @Test
    void whenAddAsset_oneAssetIsFound() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String name = "teststock";
        String input = String.format("%s -n %s -p %s", cmd, name, BigDecimal.ONE);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getAssets().getAssets().size(), is(1));
    }

    @Test
    void assetNameWithOneWord_whenAddAsset_AssetFoundWithCorrectName() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String name = "teststock";
        String input = String.format("%s -n %s -p %s", cmd, name, BigDecimal.ONE);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        Asset asset = context.getAssets().getAssetAtIndex(0).get();
        assertThat(asset.getName(), is(name));
    }

    @Test
    void assetNameWithMultipleWords_whenAddAsset_AssetFoundWithCorrectName() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String name = "test stock";
        String input = String.format("%s -n %s -p %s", cmd, name, BigDecimal.ONE);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        Asset asset = context.getAssets().getAssetAtIndex(0).get();
        assertThat(asset.getName(), is(name));
    }

    @Test
    void assetPriceWithNoDecimals_whenAddAsset_AssetIsFoundWithCorrectPrice() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String price = "3";
        String input = String.format("%s -n %s -p %s", cmd, "name", price);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        Asset asset = context.getAssets().getAssetAtIndex(0).get();
        assertThat(asset.getPricePerShare(), is(new BigDecimal(price)));
    }

    @Test
    void assetPriceWithDecimals_whenAddAsset_AssetIsFoundWithCorrectPrice() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String price = "3.002";
        String input = String.format("%s -n %s -p %s", cmd, "name", price);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        Asset asset = context.getAssets().getAssetAtIndex(0).get();
        assertThat(asset.getPricePerShare(), is(new BigDecimal(price)));
    }

    @Test
    void missingAssetNameOption_whenAddAsset_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String input = String.format("%s -p %s", cmd, BigDecimal.ONE);
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void emptyAssetName_whenAddAsset_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String input = String.format("%s -n -p %s", cmd, BigDecimal.ONE);
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void missingAssetPriceOption_whenAddAsset_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String input = String.format("%s -n %s", cmd, "name");
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void emptyAssetPrice_whenAddAsset_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String input = String.format("%s -n %s -p", cmd, "name");
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void assetPriceNotNumeric_whenAddAsset_throwsInvalidOptionsException() throws Exception {
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String input = String.format("%s -n %s -p %s", cmd, "name", "3.H");
        Command command = new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get();
        assertThrows(InvalidOptionsException.class, () -> command.run());
    }

    @Test
    void contextContainsOneAsset_whenUpdateAssetPrice_oneAssetIsFound() throws Exception {
        String name = "stock";
        Asset existing = new Asset(name, BigDecimal.TEN);
        context.getAssets().getAssets().add(existing);
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String input = String.format("%s -n %s -p %s", cmd, name, BigDecimal.ONE);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getAssets().getAssets().size(), is(1));
    }

    @Test
    void contextContainsOneAsset_whenUpdateAssetPrice_assetReflectsPriceChange() throws Exception {
        String name = "stock";
        String newPrice = "3.00";
        Asset existing = new Asset(name, BigDecimal.TEN);
        context.getAssets().getAssets().add(existing);
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String input = String.format("%s -n %s -p %s", cmd, name, new BigDecimal(newPrice));
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getAssets().getAssets().get(0).getPricePerShare(), is(new BigDecimal(newPrice)));
    }

    @Test
    void contextContainsOneAsset_whenAddNewAsset_twoAssetsFound() throws Exception {
        Asset existing = new Asset("stock 1", BigDecimal.TEN);
        context.getAssets().getAssets().add(existing);
        String cmd = AvailableCommands.ADD_OR_UPDATE_ASSET.getCommandLineInstruction();
        String input = String.format("%s -n %s -p %s", cmd, "stock 2", BigDecimal.ONE);
        new CommandBuilder().setCommandInput(input).setContext(context).buildCommand().get().run();
        assertThat(context.getAssets().getAssets().size(), is(2));
    }

}