/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.item.crops;

import io.github.sayuzaur.foodies.block.PlantLogic;
import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

import static io.github.sayuzaur.foodies.FoodiesConfig.FOOD_CONFIG;

public class Carrot extends TemplateStackableFoodItem {
    private static final Block CROP_BLOCK = BlockListener.CARROT_CROPS;

    public Carrot(Identifier identifier) {
        super(identifier, FOOD_CONFIG.rawHeal, false, FOOD_CONFIG.rawStackSize);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {
        if (CROP_BLOCK.canPlaceAt(world, x, y + 1, z) && side == 1) {
            world.setBlock(x, y + 1, z, CROP_BLOCK.id);
            stack.count--;

            PlantLogic.plantingClientEffect(world, x, y, z);
            return true;
        }
        return false;
    }
}
