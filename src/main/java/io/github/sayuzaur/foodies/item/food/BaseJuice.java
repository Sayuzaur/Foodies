/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.item.food;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

import static io.github.sayuzaur.foodies.compat.UniTweaksCompat.noFoodWastageEnabled;

public class BaseJuice extends TemplateStackableFoodItem {
    public BaseJuice(Identifier identifier, int healAmount) {
        super(identifier, healAmount, false, 3);
    }

    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (noFoodWastageEnabled() && user.health >= 20) {
            return stack;
        } else {
            super.use(stack, world, user);
            if (!world.isRemote) {
                ItemStack bottleStack = new ItemStack(ItemListener.JAR);
                ItemEntity bottleItemEntity = new ItemEntity(world, user.x, user.y, user.z, bottleStack);
                world.spawnEntity(bottleItemEntity);
            }
            return stack;
        }
    }
}
