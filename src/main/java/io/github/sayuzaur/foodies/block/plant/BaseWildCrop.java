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

package io.github.sayuzaur.foodies.block.plant;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.context.BlockTagContext;
import net.modificationstation.stationapi.api.registry.tag.BlockTags;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class BaseWildCrop extends TemplateBlock {
    public BaseWildCrop(Identifier identifier) {
        super(identifier, Material.PLANT);
        this.setTickRandomly(true);
        this.setBoundingBox(0.125F, 0.0F, 0.125F, 0.875F, 0.875F, 0.875F);
        this.setSoundGroup(DIRT_SOUND_GROUP);
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    public boolean canPlantOnTop(World world, int x, int y, int z) {
        return     world.getBlockState(x, y, z).isIn(BlockTags.GRASS_BLOCKS, BlockTagContext.of(world, x, y, z))
                || world.getBlockState(x, y, z).isIn(BlockTags.DIRTS, BlockTagContext.of(world, x, y, z));
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z, int side) {
        if (!world.isAir(x, y, z)) {
            return false;
        }
        return canPlantOnTop(world, x, y - 1, z);
    }

    @Override
    public boolean canGrow(World world, int x, int y, int z) {
        return canPlantOnTop(world, x, y - 1, z);
    }

    protected final void breakIfCannotGrow(World world, int x, int y, int z) {
        if (!this.canGrow(world, x, y, z)) {
            this.dropStacks(world, x, y, z, world.getBlockMeta(x, y, z));
            world.setBlock(x, y, z, 0);
        }
    }

    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);
        this.breakIfCannotGrow(world, x, y, z);
    }

    public void onTick(World world, int x, int y, int z, Random random) {
        this.breakIfCannotGrow(world, x, y, z);
    }

    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        if (!world.isRemote && playerEntity.getHand() != null && playerEntity.getHand().itemId == Item.SHEARS.id) {
            this.dropStack(world, x, y, z, new ItemStack(this.asItem()));
        } else {
            super.afterBreak(world, playerEntity, x, y, z, meta);
        }
    }
}
