package io.github.sayuzaur.foodies.world.feature;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;
import java.util.Random;

public class WildOnionPatchFeature extends Feature {
    public static List<String> targetBiomes = List.of(
            "Forest"
    );

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (random.nextInt(64) != 0) {
            return false;
        }
        for (int i = 0; i < 48; i++) {
            int varX = x + random.nextInt(6) - random.nextInt(4);
            int varY = y + random.nextInt(4) - random.nextInt(4);
            int varZ = z + random.nextInt(6) - random.nextInt(4);

            if (!world.isAir(varX, varY, varZ)) {
                continue;
            }

            if (!BlockListener.ONION_WILD.canPlaceAt(world, varX, varY, varZ)) {
                continue;
            }

            world.setBlockWithoutNotifyingNeighbors(varX, varY, varZ, BlockListener.ONION_WILD.id);
        }

        return true;
    }
}
