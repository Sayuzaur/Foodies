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
