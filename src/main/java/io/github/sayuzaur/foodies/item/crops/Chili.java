/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.item.crops;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

import static io.github.sayuzaur.foodies.FoodiesMod.FOOD_CONFIG;

public class Chili extends TemplateStackableFoodItem{
    public Chili(Identifier identifier) {
        super(identifier, FOOD_CONFIG.rawHeal, false, FOOD_CONFIG.rawStackSize);
    }
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        super.use(stack, world, user);
        user.fireTicks = 100;
        return stack;
    }
}
