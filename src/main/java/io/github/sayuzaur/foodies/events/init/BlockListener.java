package io.github.sayuzaur.foodies.events.init;

import io.github.sayuzaur.foodies.block.SaltBlock;
import io.github.sayuzaur.foodies.block.SaltOre;
import io.github.sayuzaur.foodies.block.crops.CarrotCrops;
import io.github.sayuzaur.foodies.block.crops.PotatoCrops;
import io.github.sayuzaur.foodies.block.crops.TomatoCrops;
import io.github.sayuzaur.foodies.block.plant.CarrotWild;
import io.github.sayuzaur.foodies.block.plant.PotatoWild;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;

import java.lang.invoke.MethodHandles;

import static io.github.sayuzaur.foodies.FoodiesMod.NAMESPACE;

public class BlockListener {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    public static Block SALT_ORE;
    public static Block SALT_BLOCK;

    public static Block CARROT_CROPS;
    public static Block CARROT_WILD;
    public static Block POTATO_CROPS;
    public static Block POTATO_WILD;
    public static Block TOMATO_CROPS;

    @EventListener
    private static void registerBlocks(BlockRegistryEvent event){
        SALT_ORE = new SaltOre(NAMESPACE.id("salt_ore")).setTranslationKey(NAMESPACE.id("salt_ore"));
        SALT_BLOCK = new SaltBlock(NAMESPACE.id("salt_block")).setTranslationKey(NAMESPACE.id("salt_block"));

        CARROT_CROPS = new CarrotCrops(NAMESPACE.id("carrot_crops")).setTranslationKey(NAMESPACE.id("carrot_crops"));
        CARROT_WILD = new CarrotWild(NAMESPACE.id("carrot_wild")).setTranslationKey(NAMESPACE.id("carrot_wild"));
        POTATO_CROPS = new PotatoCrops(NAMESPACE.id("potato_crops")).setTranslationKey(NAMESPACE.id("potato_crops"));
        POTATO_WILD = new PotatoWild(NAMESPACE.id("potato_wild")).setTranslationKey(NAMESPACE.id("potato_wild"));
        TOMATO_CROPS = new TomatoCrops(NAMESPACE.id("tomato_crops")).setTranslationKey(NAMESPACE.id("tomato_crops"));
    }
}
