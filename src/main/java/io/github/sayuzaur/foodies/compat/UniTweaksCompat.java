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
