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

package io.github.sayuzaur.foodies.item;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import static io.github.sayuzaur.foodies.FoodiesMod.FOOD_CONFIG;

public class Jar extends TemplateItem {
    public Jar(Identifier identifier) {
        super(identifier);
        this.setMaxCount(16);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        int blockId = world.getBlockId(x, y, z);
        if (blockId == Block.CACTUS.id) {
            if (!world.isRemote) {
                ItemStack bottleStack = new ItemStack(ItemListener.JUICE_CACTUS);
                ItemEntity bottleItemEntity = new ItemEntity(world, user.x, user.y, user.z, bottleStack);
                world.spawnEntity(bottleItemEntity);

                --stack.count;
                if (FOOD_CONFIG.breakCactus) {
                    world.setBlock(x, y, z, 0);
                    world.playSound(x, y, z, "step.cloth", 1.0F, 1.0F);
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
