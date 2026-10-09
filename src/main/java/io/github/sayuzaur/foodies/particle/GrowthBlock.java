/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.particle;

import net.minecraft.world.World;

public class GrowthBlock extends PlantGlint{
    public GrowthBlock(World world, int x, int y, int z) {
        super(world, x, y, z);
    }

    @Override
    protected String getTextureName() {
        return "glint_gold_";
    }

    @Override
    protected int getTextureCount() {
        return 3;
    }
}
