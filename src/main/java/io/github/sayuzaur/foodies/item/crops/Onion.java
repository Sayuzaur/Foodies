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

package io.github.sayuzaur.foodies.item.crops;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

import static io.github.sayuzaur.foodies.FoodiesMod.FOOD_CONFIG;

public class Onion extends TemplateStackableFoodItem {
    public Onion(Identifier identifier){
        super(identifier, FOOD_CONFIG.rawHeal, false, FOOD_CONFIG.rawStackSize);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {

        if (world.getBlockId(x, y, z) == Block.FARMLAND.id && side == 1) {
            world.setBlock(x, y + 1, z, BlockListener.ONION_CROPS.id);
            stack.count--;
            return true;
        }
        return false;
    }
}
