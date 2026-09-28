/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.compat;

import net.fabricmc.loader.api.FabricLoader;

public class UniTweaksCompat {
    public static boolean noFoodWastageEnabled() {
        if (!FabricLoader.getInstance().isModLoaded("unitweaks")) {
            return false;
        }
        try {
            Class<?> configClass =
                    Class.forName("net.danygames2014.unitweaks.UniTweaks");

            Object gameplayConfig =
                    configClass.getField("GAMEPLAY_CONFIG").get(null);

            Boolean value = (Boolean) gameplayConfig.getClass()
                    .getField("noFoodWastage")
                    .get(gameplayConfig);

            return value;
        }
        catch (Exception e) {
            return false;
        }
    }
}
