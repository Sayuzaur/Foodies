/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.modificationstation.stationapi.api.util.Namespace;

public class FoodiesMod {
    @SuppressWarnings("UnstableApiUsage")
    public static final Namespace NAMESPACE = Namespace.resolve();

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

    //TODO Add beenest block tag
}
