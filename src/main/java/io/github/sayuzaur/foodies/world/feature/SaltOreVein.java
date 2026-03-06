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
