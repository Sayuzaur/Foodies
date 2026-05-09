/*
 * Copyright (C) 2026 Sayuzaur
 *
 * This file is part of Foodies.
 * Foodies is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Foodies is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with Foodies.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.sayuzaur.foodies.events.init;

import io.github.sayuzaur.foodies.block.BeeHive;
import io.github.sayuzaur.foodies.block.CookingStation;
import io.github.sayuzaur.foodies.block.SaltBlock;
import io.github.sayuzaur.foodies.block.SaltOre;
import io.github.sayuzaur.foodies.block.crops.*;
import io.github.sayuzaur.foodies.block.plant.*;
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
    public static Block ONION_CROPS;
    public static Block ONION_WILD;
    public static Block TOMATO_CROPS;
    public static Block TOMATO_WILD;
    public static Block CABBAGE_CROPS;
    public static Block CABBAGE_WILD;
    public static Block CHILI_CROPS;

    public static Block COOKING_STATION;

    public static Block BEEHIVE_OAK;
    public static Block BEENEST_OAK;
    public static Block BEENEST_BIRCH;
    public static Block HONEYCOMB;

    @EventListener
    private static void registerBlocks(BlockRegistryEvent event) {
        SALT_ORE = new SaltOre(NAMESPACE.id("salt_ore")).setTranslationKey(NAMESPACE.id("salt_ore"));
        SALT_BLOCK = new SaltBlock(NAMESPACE.id("salt_block")).setTranslationKey(NAMESPACE.id("salt_block"));

        CARROT_CROPS = new CarrotCrops(NAMESPACE.id("carrot_crops")).setTranslationKey(NAMESPACE.id("carrot_crops"));
        CARROT_WILD = new CarrotWild(NAMESPACE.id("carrot_wild")).setTranslationKey(NAMESPACE.id("carrot_wild"));
        POTATO_CROPS = new PotatoCrops(NAMESPACE.id("potato_crops")).setTranslationKey(NAMESPACE.id("potato_crops"));
        POTATO_WILD = new PotatoWild(NAMESPACE.id("potato_wild")).setTranslationKey(NAMESPACE.id("potato_wild"));
        ONION_CROPS = new OnionCrops(NAMESPACE.id("onion_crops")).setTranslationKey(NAMESPACE.id("onion_crops"));
        ONION_WILD = new OnionWild(NAMESPACE.id("onion_wild")).setTranslationKey(NAMESPACE.id("onion_wild"));
        TOMATO_CROPS = new TomatoCrops(NAMESPACE.id("tomato_crops")).setTranslationKey(NAMESPACE.id("tomato_crops"));
        TOMATO_WILD = new TomatoWild(NAMESPACE.id("tomato_wild")).setTranslationKey(NAMESPACE.id("tomato_wild"));
        CABBAGE_CROPS = new CabbageCrops(NAMESPACE.id("cabbage_crops")).setTranslationKey(NAMESPACE.id("cabbage_crops"));
        CABBAGE_WILD = new CabbageWild(NAMESPACE.id("cabbage_wild")).setTranslationKey(NAMESPACE.id("cabbage_wild"));
        CHILI_CROPS = new ChiliCrops(NAMESPACE.id("chili_crops")).setTranslationKey(NAMESPACE.id("chili_crops"));

        COOKING_STATION = new CookingStation(NAMESPACE.id("cooking_station")).setTranslationKey(NAMESPACE.id("cooking_station"));

        BEEHIVE_OAK = new BeeHive(NAMESPACE.id("beehive_oak")).setTranslationKey(NAMESPACE.id("beehive_oak"));
        BEENEST_OAK = new BeeHive(NAMESPACE.id("beenest_oak")).setTranslationKey(NAMESPACE.id("beenest_oak"));
        BEENEST_BIRCH = new BeeHive(NAMESPACE.id("beenest_birch")).setTranslationKey(NAMESPACE.id("beenest_birch"));
        HONEYCOMB = new TemplateBlock(NAMESPACE.id("honeycomb"), Material.SOIL).setHardness(0.8F).setTranslationKey(NAMESPACE.id("honeycomb"));
    }
}
