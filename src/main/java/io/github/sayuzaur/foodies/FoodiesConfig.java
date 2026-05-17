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

import net.glasslauncher.mods.gcapi3.api.ConfigCategory;
import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class FoodiesConfig {

    @ConfigCategory(name = "Food Behaviour")
    public FoodConfig basefood = new FoodConfig();

    @ConfigCategory(name = "Features Generation")
    public FeaturesGenConfig featuregen = new FeaturesGenConfig();

    @ConfigCategory(name = "Beehive Visuals")
    public BeeHiveClientConfig beehiveclient = new BeeHiveClientConfig();

    @ConfigCategory(name = "Mob Drops")
    public MobDropsConfig mobdrops = new MobDropsConfig();

    public static class FoodConfig {
        @ConfigEntry(name = "Raw crops stack size (1-64)", minValue = 1, maxValue = 64, description = "Raw crops being carrots, potatoes, tomatoes, onions, cabbage and chili.", requiresRestart = true)
        public Integer rawStackSize = 64;

        @ConfigEntry(name = "Raw crops heal value (0-2)", minValue = 0, maxValue = 2, description = "How much raw crops heal you when eaten.", requiresRestart = true)
        public Integer rawHeal = 1;

        @ConfigEntry(name = "Juicing cactus breaks block", description = "If right-clicking with bottle on cactus breaks this block.")
        public Boolean breakCactus = true;
    }

    public static class FeaturesGenConfig {
        @ConfigEntry(name = "Oak Tree with Bee Nest rarity", minValue = 1, maxValue = 16, description = "1 -> Common, 16 -> Very hard to find", requiresRestart = true)
        public Integer oakTreeBeeChance = 8;

        @ConfigEntry(name = "Birch Tree with Bee Nest rarity", minValue = 1, maxValue = 16, description = "1 -> Common, 16 -> Very hard to find", requiresRestart = true)
        public Integer birchTreeBeeChance = 6;
    }

    public static class BeeHiveClientConfig {
        @ConfigEntry(name = "Bee Particles num subtracting", minValue = 1,  maxValue = 64, description = "1 -> Max Bees Particles Rate, 64 - > Sparse")
        public Integer beeParticlesNum = 1;

        @ConfigEntry(name = "Show Bees on Flowers", description = "Might have small performance impact")
        public Boolean beesOnFlowers = true;

        @ConfigEntry(name = "Bees Sound Volume multiplier", minValue = 0, maxValue = 5, description = "0.0F -> Mute")
        public Float beesSoundVolume = 1.0F;
    }

    public static class MobDropsConfig {
        @ConfigEntry(name = "Beef drop chance %", minValue = 0,  maxValue = 100, description = "0%-100%")
        public Integer beefDropChance = 33;

        @ConfigEntry(name = "Calamari drop chance %", minValue = 0,  maxValue = 100, description = "0%-100%")
        public Integer calamariDropChance = 33;

        @ConfigEntry(name = "Chicken meat drop chance %", minValue = 0,  maxValue = 100, description = "0%-100%")
        public Integer chickenDropChance = 33;

        @ConfigEntry(name = "Mutton drop chance %", minValue = 0,  maxValue = 100, description = "0%-100%")
        public Integer muttonDropChance = 33;

        @ConfigEntry(name = "Cooked Porkchop drop chance %", minValue = 0,  maxValue = 100, description = "Cooked Porkchop dropped from ZombiePigman")
        public Integer porkchopZombiePigDropChance = 33;

        @ConfigEntry(name = "Chili drop chance %", minValue = 0,  maxValue = 100, description = "Chili dropped from ZombiePigman")
        public Integer chiliZombiePigDropChance = 33;
    }
}

