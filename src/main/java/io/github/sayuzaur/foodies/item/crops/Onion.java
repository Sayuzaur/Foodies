/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
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
        if (!world.isAir(x, y + 1, z)) {
            return false;
        }
        if (world.getBlockId(x, y, z) == Block.FARMLAND.id && side == 1) {
            world.setBlock(x, y + 1, z, BlockListener.ONION_CROPS.id);
            stack.count--;
            return true;
        }
        return false;
    }
}
