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
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.api.util.Identifier;

public class MysterySeeds extends TemplateItem implements CustomTooltipProvider {
    public MysterySeeds(Identifier identifier){
        super(identifier);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {

        if (world.getBlockId(x, y, z) == Block.FARMLAND.id && side == 1) {
            int randCrop = random.nextInt(5);

            switch (randCrop) {
                case 0 -> world.setBlock(x, y + 1, z, BlockListener.CARROT_CROPS.id);
                case 1 -> world.setBlock(x, y + 1, z, BlockListener.POTATO_CROPS.id);
                case 2 -> world.setBlock(x, y + 1, z, BlockListener.ONION_CROPS.id);
                case 3 -> world.setBlock(x, y + 1, z, BlockListener.TOMATO_CROPS.id);
                case 4 -> world.setBlock(x, y + 1, z, BlockListener.CABBAGE_CROPS.id);
            }

            stack.count--;
            return true;
        }
        return false;
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        return new String[]{originalTooltip,
                Formatting.GRAY + "Can grow into any overworld vegetable"};
    }
}
