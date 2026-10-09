/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.particle;

import net.minecraft.world.World;

public class Bonemeal extends PlantGlint{
    public Bonemeal(World world, int x, int y, int z) {
        super(world, x, y, z);
    }

    @Override
    protected String getTextureName() {
        return "glint_green_";
    }

    @Override
    protected int getTextureCount() {
        return 3;
    }
}
