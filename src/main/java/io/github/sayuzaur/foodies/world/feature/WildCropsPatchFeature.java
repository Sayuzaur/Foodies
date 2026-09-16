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

public class WildCropsPatchFeature extends Feature {
    protected int maxPlants;
    protected int range;
    protected int plantType;

    public WildCropsPatchFeature(int patchSize, int spread, int plantMode) {
        this.maxPlants = patchSize;
        this.range = spread;
        this.plantType = plantMode;
    }

    public boolean generate(World world, Random random, int x, int y, int z) {
        Block wildCropsBlock;
        int attempts = 64;
        int generatedPlants = 0;
        int patchSizeRandSize = random.nextInt(3) - 1;
        generatedPlants = generatedPlants + patchSizeRandSize;

        switch (plantType) {
            case 1 -> wildCropsBlock = BlockListener.CARROT_WILD;
            case 2 -> wildCropsBlock = BlockListener.POTATO_WILD;
            case 3 -> wildCropsBlock = BlockListener.ONION_WILD;
            case 4 -> wildCropsBlock = BlockListener.TOMATO_WILD;
            case 5 -> wildCropsBlock = BlockListener.CABBAGE_WILD;
            default -> wildCropsBlock = Block.ROSE;
        }

        for (int i = 0; i < attempts && generatedPlants < this.maxPlants; i++) {
            int varX = x + random.nextInt(range) - random.nextInt(range);
            int varZ = z + random.nextInt(range) - random.nextInt(range);
            int varY = y + random.nextInt(range/2) - random.nextInt(range/2);

            if (world.isAir(varX, varY, varZ) && wildCropsBlock.canGrow(world, varX, varY, varZ)) {
                world.setBlockWithoutNotifyingNeighbors(varX, varY, varZ, wildCropsBlock.id);
                generatedPlants++;
            }
        }
        return true;
    }
}
