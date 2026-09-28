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

import io.github.sayuzaur.foodies.block.*;
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

    public static Block LEAVES_APPLE;
    public static Block LEAVES_ORANGE;
    public static Block LEAVES_PEACH;

    public static Block FRUIT_TREE_TRUNK;

    public static Block CLOUDBERRY_BUSH;

    public static Block CRATE_APPLE;
    public static Block CRATE_ORANGE;
    public static Block CRATE_PEACH;

    @EventListener
    private static void registerBlocks(BlockRegistryEvent event) {
        SALT_ORE = new SaltOre(NAMESPACE.id("salt_ore"));
        SALT_BLOCK = new SaltBlock(NAMESPACE.id("salt_block"));

        CARROT_CROPS = new CarrotCrops(NAMESPACE.id("carrot_crops"));
        CARROT_WILD = new CarrotWild(NAMESPACE.id("carrot_wild"));
        POTATO_CROPS = new PotatoCrops(NAMESPACE.id("potato_crops"));
        POTATO_WILD = new PotatoWild(NAMESPACE.id("potato_wild"));
        ONION_CROPS = new OnionCrops(NAMESPACE.id("onion_crops"));
        ONION_WILD = new OnionWild(NAMESPACE.id("onion_wild"));
        TOMATO_CROPS = new TomatoCrops(NAMESPACE.id("tomato_crops"));
        TOMATO_WILD = new TomatoWild(NAMESPACE.id("tomato_wild"));
        CABBAGE_CROPS = new CabbageCrops(NAMESPACE.id("cabbage_crops"));
        CABBAGE_WILD = new CabbageWild(NAMESPACE.id("cabbage_wild"));
        CHILI_CROPS = new ChiliCrops(NAMESPACE.id("chili_crops"));

        COOKING_STATION = new CookingStation(NAMESPACE.id("cooking_station"));

        BEEHIVE_OAK = new BeeHive(NAMESPACE.id("beehive_oak"));
        BEENEST_OAK = new BeeHive(NAMESPACE.id("beenest_oak"));
        BEENEST_BIRCH = new BeeHive(NAMESPACE.id("beenest_birch"));
        HONEYCOMB = new TemplateBlock(NAMESPACE.id("honeycomb"), Material.SOIL).setHardness(0.8F);

        LEAVES_APPLE = new FruitTreeLeaves(NAMESPACE.id("leaves_apple"));
        LEAVES_ORANGE = new FruitTreeLeaves(NAMESPACE.id("leaves_orange"));
        LEAVES_PEACH = new FruitTreeLeaves(NAMESPACE.id("leaves_peach"));

        FRUIT_TREE_TRUNK = new FruitTreeTrunk(NAMESPACE.id("fruit_tree_trunk"));

        CLOUDBERRY_BUSH = new Cloudberry(NAMESPACE.id("cloudberry_bush"));

        CRATE_APPLE = new Crate(NAMESPACE.id("crate_apple"));
        CRATE_ORANGE = new Crate(NAMESPACE.id("crate_orange"));
        CRATE_PEACH = new Crate(NAMESPACE.id("crate_peach"));
    }
}
