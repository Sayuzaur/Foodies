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

package io.github.sayuzaur.foodies.world.feature;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;
import java.util.Random;

public class WildTomatoPatchFeature extends Feature {
    public static List<String> targetBiomes = List.of(
            "Shrubland",
            "Savanna"
    );

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (random.nextInt(128) != 0){
            return false;
        }
        for (int i = 0; i < 32; i++) {
            int varX = x + random.nextInt(6) - random.nextInt(8);
            int varY = y + random.nextInt(4) - random.nextInt(4);
            int varZ = z + random.nextInt(6) - random.nextInt(8);

            if (!world.isAir(varX, varY, varZ)) {
                continue;
            }

            if (!BlockListener.TOMATO_WILD.canPlaceAt(world, varX, varY, varZ)) {
                continue;
            }

            world.setBlockWithoutNotifyingNeighbors(varX, varY, varZ, BlockListener.TOMATO_WILD.id);
        }

        return true;
    }
}
