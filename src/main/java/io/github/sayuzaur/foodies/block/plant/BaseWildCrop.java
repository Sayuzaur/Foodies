/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block.plant;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class BaseWildCrop extends BasePlant {
    public BaseWildCrop(Identifier identifier) {
        super(identifier);
        this.setTickRandomly(true);
        this.setBoundingBox(0.125F, 0.0F, 0.125F, 0.875F, 0.875F, 0.875F);
    }

    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        if (!world.isRemote && playerEntity.getHand() != null && playerEntity.getHand().itemId == Item.SHEARS.id) {
            this.dropStack(world, x, y, z, new ItemStack(this.asItem()));
        } else {
            super.afterBreak(world, playerEntity, x, y, z, meta);
        }
    }
}
