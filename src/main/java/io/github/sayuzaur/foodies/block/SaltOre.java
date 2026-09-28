/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
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

