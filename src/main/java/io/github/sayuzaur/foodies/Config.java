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

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class Config {
    public static class FoodConfig {

        @ConfigEntry(name = "Raw crops stack size (1-64)", minValue = 1, maxValue = 64, description = "Raw crops being carrots, potatoes, tomatoes, onions, cabbage and chili.", requiresRestart = true)
        public Integer rawStackSize = 64;

        @ConfigEntry(name = "Raw crops heal value (0-2)", minValue = 0, maxValue = 2, description = "How much raw crops heal you when eaten.", requiresRestart = true)
        public Integer rawHeal = 1;

        @ConfigEntry(name = "Juicing cactus breaks block", description = "If right-clicking with bottle on cactus breaks this block.")
        public Boolean breakCactus = true;
    }
}
