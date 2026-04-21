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

package io.github.sayuzaur.foodies.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import paulevs.bhcreative.registry.TabRegistryEvent;

import static paulevs.bhcreative.listeners.VanillaTabListener.*;

public class CreativeListener {

    @EventListener
    public void onTabInit(TabRegistryEvent event) {
        tabFood.addItem(new ItemStack(ItemListener.CARROT));
        tabFood.addItem(new ItemStack(ItemListener.POTATO));
        tabFood.addItem(new ItemStack(ItemListener.POTATO_BAKED));
        tabFood.addItem(new ItemStack(ItemListener.ONION));
        tabFood.addItem(new ItemStack(ItemListener.TOMATO));
        tabFood.addItem(new ItemStack(ItemListener.TOMATO_SEEDS));
        tabFood.addItem(new ItemStack(ItemListener.CABBAGE));
        tabFood.addItem(new ItemStack(ItemListener.CABBAGE_SEEDS));
        tabFood.addItem(new ItemStack(ItemListener.MYSTERY_SEEDS));
        tabFood.addItem(new ItemStack(ItemListener.CHILI));
        tabFood.addItem(new ItemStack(ItemListener.CHILI_SEEDS));

        tabFood.addItem(new ItemStack(ItemListener.BEEF_RAW));
        tabFood.addItem(new ItemStack(ItemListener.BEEF_COOKED));
        tabFood.addItem(new ItemStack(ItemListener.CHICKEN_RAW));
        tabFood.addItem(new ItemStack(ItemListener.CHICKEN_COOKED));
        tabFood.addItem(new ItemStack(ItemListener.CHICKEN_DRUMSTICK_RAW));
        tabFood.addItem(new ItemStack(ItemListener.CHICKEN_DRUMSTICK_COOKED));
        tabFood.addItem(new ItemStack(ItemListener.MUTTON_RAW));
        tabFood.addItem(new ItemStack(ItemListener.MUTTON_COOKED));
        tabFood.addItem(new ItemStack(ItemListener.CALAMARI_RAW));
        tabFood.addItem(new ItemStack(ItemListener.CALAMARI_COOKED));
        tabFood.addItem(new ItemStack(ItemListener.EGG_COOKED));

        tabFood.addItem(new ItemStack(ItemListener.OMELET));
        tabFood.addItem(new ItemStack(ItemListener.FISHANDCHIPS));
        tabFood.addItem(new ItemStack(ItemListener.WHITECHEESE));
        tabFood.addItem(new ItemStack(ItemListener.STUFFED_CABBAGE));
        tabFood.addItem(new ItemStack(ItemListener.STUFFED_CHILI));
        tabFood.addItem(new ItemStack(ItemListener.BRUSCHETTA));

        tabFood.addItem(new ItemStack(ItemListener.STEW_CARROT));
        tabFood.addItem(new ItemStack(ItemListener.STEW_TOMATO));
        tabFood.addItem(new ItemStack(ItemListener.STEW_MEAT));
        tabFood.addItem(new ItemStack(ItemListener.STEW_FISH));
        tabFood.addItem(new ItemStack(ItemListener.STEW_ONION));

        tabFood.addItem(new ItemStack(ItemListener.PICO_DE_GALLO));
        tabFood.addItem(new ItemStack(ItemListener.SHAKSHOUKA));

        tabFood.addItem(new ItemStack(ItemListener.SANDWICH, 1, 1));

        tabFood.addItem(new ItemStack(ItemListener.BOTTLE));
        tabFood.addItem(new ItemStack(ItemListener.JUICE_CACTUS));
        tabFood.addItem(new ItemStack(ItemListener.JUICE_APPLE));
        tabFood.addItem(new ItemStack(ItemListener.JUICE_TOMATO));

        tabFood.addItem(new ItemStack(ItemListener.PIE_CARROT));
        tabFood.addItem(new ItemStack(ItemListener.PIE_APPLE));

        tabFood.addItem(new ItemStack(ItemListener.SALT));

        tabFullBlocks.addItem(new ItemStack(BlockListener.SALT_ORE));
        tabFullBlocks.addItem(new ItemStack(BlockListener.SALT_BLOCK));
        tabOtherBlocks.addItem(new ItemStack(BlockListener.COOKING_STATION));
    }

}
