/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.world.feature;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.block.context.BlockTagContext;
import net.modificationstation.stationapi.api.registry.tag.BlockTags;

import java.util.Random;

public class SaltOreVein extends Feature {
    @Override
    public boolean generate(World world, Random random, int x, int y, int z){
        for (int i = 0; i <= 6; i++){
            int varX = x + random.nextInt(2);
            int varY = y + random.nextInt(2);
            int varZ = z + random.nextInt(2);
            if (world.getBlockState(varX, varY, varZ).isIn(BlockTags.ORE_BEARING_GROUND_STONE, BlockTagContext.of(world, varX, varY, varZ))) {
                world.setBlockWithoutNotifyingNeighbors(varX, varY, varZ, BlockListener.SALT_ORE.id);
            }
        }

        return true;
    }
}
