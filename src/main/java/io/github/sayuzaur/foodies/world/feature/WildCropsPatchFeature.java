/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
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
