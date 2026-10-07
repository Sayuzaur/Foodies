/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies;

import net.glasslauncher.mods.gcapi3.api.ConfigCategory;
import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class FoodiesConfig {

    //CONFIG
    @ConfigRoot(value = "foodconfig", visibleName = "Food Behaviour", index = 0)
    public static final FoodiesConfig.FoodConfig FOOD_CONFIG = new FoodiesConfig.FoodConfig();

    @ConfigRoot(value = "cropsconfig", visibleName = "Crops Growth", index = 1)
    public static final FoodiesConfig.CropsConfig CROPS_CONFIG = new FoodiesConfig.CropsConfig();

    @ConfigRoot(value = "genconfig", visibleName = "Features Generation", index = 2)
    public static final FoodiesConfig.FeaturesGenConfig GEN_CONFIG = new FoodiesConfig.FeaturesGenConfig();

    @ConfigRoot(value = "beehiveclientconfig", visibleName = "Beehive Client-Side", index = 3)
    public static final FoodiesConfig.BeeHiveClientConfig BEEHIVE_CLIENT_CONFIG = new FoodiesConfig.BeeHiveClientConfig();

    @ConfigRoot(value = "mobdropsconfig", visibleName = "Mob Drops", index = 4)
    public static final FoodiesConfig.MobDropsConfig MOB_DROPS_CONFIG = new FoodiesConfig.MobDropsConfig();

    @ConfigCategory(name = "Food Behaviour")
    public FoodConfig basefood = new FoodConfig();

    @ConfigCategory(name = "Crops Growth")
    public CropsConfig crops = new CropsConfig();

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

    public static class CropsConfig {
        @ConfigEntry(name = "Light level for crops (0-15)", minValue = 0, maxValue = 15, description = "Light level required for most crops/plants/fruits to grow. Vanilla Minecraft uses 9.")
        public Integer lightLevelRequired = 9;

        @ConfigEntry(name = "Block growth item", description = "Item identifier used to block growth. You can see item identifiers using AMI.", multiplayerSynced = true)
        public String blockGrowthItem = "minecraft:gold_ingot";

        //public Boolean breakCactus = true;
    }

    public static class FeaturesGenConfig {
        @ConfigEntry(name = "Oak Tree with Bee Nest rarity", minValue = 1, maxValue = 128, description = "1 tree in every X chunks", requiresRestart = true)
        public Integer oakTreeBeeChance = 8;

        @ConfigEntry(name = "Birch Tree with Bee Nest rarity", minValue = 1, maxValue = 128, description = "1 tree in every X chunks", requiresRestart = true)
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

