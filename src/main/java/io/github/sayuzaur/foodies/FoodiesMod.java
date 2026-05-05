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

package io.github.sayuzaur.foodies;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Namespace;

public class FoodiesMod {
    @SuppressWarnings("UnstableApiUsage")
    public static final Namespace NAMESPACE = Namespace.resolve();

    //CONFIG
    @ConfigRoot(value = "foodconfig", visibleName = "Food Behaviour", index = 0)
    public static final FoodiesConfig.FoodConfig FOOD_CONFIG = new FoodiesConfig.FoodConfig();

    @ConfigRoot(value = "genconfig", visibleName = "Features Generation", index = 2)
    public static final FoodiesConfig.FeaturesGenConfig GEN_CONFIG = new FoodiesConfig.FeaturesGenConfig();

    @ConfigRoot(value = "beehiveclientconfig", visibleName = "Beehive Client-Side", index = 1)
    public static final FoodiesConfig.BeeHiveClientConfig BEEHIVE_CLIENT_CONFIG = new FoodiesConfig.BeeHiveClientConfig();

    //BLOCK TAG
    public static final TagKey<Block> FLOWERS = TagKey.of(BlockRegistry.KEY, NAMESPACE.id("flowers"));
}
