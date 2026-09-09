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

package io.github.sayuzaur.foodies.block;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SaltOre extends TemplateBlock {
    public SaltOre(Identifier identifier){
        super(identifier, Material.STONE);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setSoundGroup(STONE_SOUND_GROUP);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        Item dropItem = ItemListener.SALT;
        int dropCount = 4 + world.random.nextInt(3);

        drops.add(new ItemStack(dropItem, dropCount));
        return drops;
    }
}

