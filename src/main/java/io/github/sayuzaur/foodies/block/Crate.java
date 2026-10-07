/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class Crate extends TemplateBlock {
    public Crate(Identifier identifier) {
        super(identifier, Material.WOOD);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setSoundGroup(WOOD_SOUND_GROUP);
    }

    @Override
    public void onDestroyedByExplosion(World world, int x, int y, int z) {
        //TODO Make it drop crops/fruits it contains instead of block. Same for HAYBALE
    }
}
