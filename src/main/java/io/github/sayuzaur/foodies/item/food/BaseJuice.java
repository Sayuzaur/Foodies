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

package io.github.sayuzaur.foodies.item.food;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BaseJuice extends TemplateStackableFoodItem {
    public BaseJuice(Identifier identifier, int healAmount) {
        super(identifier, healAmount, false, 3);
    }

    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        super.use(stack, world, user);

        ItemStack bottleStack = new ItemStack(ItemListener.BOTTLE);
        ItemEntity bottleItemEntity = new ItemEntity(world, user.x, user.y - 1.7F, user.z, bottleStack);
        world.spawnEntity(bottleItemEntity);

        return stack;
    }
}
