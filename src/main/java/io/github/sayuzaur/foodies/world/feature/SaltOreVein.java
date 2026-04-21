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
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SaltOreVein extends Feature {

    @Override
    public boolean generate(World world, Random random, int x, int y, int z){

        for (int i = 0; i <= 6; i++){
            int varX = x + random.nextInt(2);
            int varY = y + random.nextInt(2);
            int varZ = z + random.nextInt(2);
            if (world.getBlockId(varX, varY, varZ) == Block.STONE.id){
                world.setBlockWithoutNotifyingNeighbors(varX, varY, varZ, BlockListener.SALT_ORE.id);
            }
        }
        return true;
    }
}
