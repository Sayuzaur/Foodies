/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

import static io.github.sayuzaur.foodies.FoodiesMod.NAMESPACE;

public class CreativeListener {
    public static CreativeTab tabFoodies;

    @EventListener
    public void onTabInit(TabRegistryEvent event) {
        tabFoodies = new SimpleTab(NAMESPACE.id("foodies_creative_tab"), ItemListener.TOMATO);
        event.register(tabFoodies);

        tabFoodies.addItem(new ItemStack(ItemListener.CARROT));
        tabFoodies.addItem(new ItemStack(ItemListener.POTATO));
        tabFoodies.addItem(new ItemStack(ItemListener.POTATO_BAKED));
        tabFoodies.addItem(new ItemStack(ItemListener.ONION));
        tabFoodies.addItem(new ItemStack(ItemListener.TOMATO));
        tabFoodies.addItem(new ItemStack(ItemListener.TOMATO_SEEDS));
        tabFoodies.addItem(new ItemStack(ItemListener.CABBAGE));
        tabFoodies.addItem(new ItemStack(ItemListener.CABBAGE_SEEDS));
        tabFoodies.addItem(new ItemStack(ItemListener.MYSTERY_SEEDS));
        tabFoodies.addItem(new ItemStack(ItemListener.CHILI));
        tabFoodies.addItem(new ItemStack(ItemListener.CHILI_SEEDS));

        tabFoodies.addItem(new ItemStack(ItemListener.BEEF_RAW));
        tabFoodies.addItem(new ItemStack(ItemListener.BEEF_COOKED));
        tabFoodies.addItem(new ItemStack(ItemListener.BEEF_GLAZED));
        tabFoodies.addItem(new ItemStack(ItemListener.CHICKEN_RAW));
        tabFoodies.addItem(new ItemStack(ItemListener.CHICKEN_COOKED));
        tabFoodies.addItem(new ItemStack(ItemListener.CHICKEN_GLAZED));
        tabFoodies.addItem(new ItemStack(ItemListener.CHICKEN_DRUMSTICK_RAW));
        tabFoodies.addItem(new ItemStack(ItemListener.CHICKEN_DRUMSTICK_COOKED));
        tabFoodies.addItem(new ItemStack(ItemListener.CHICKEN_DRUMSTICK_GLAZED));
        tabFoodies.addItem(new ItemStack(ItemListener.MUTTON_RAW));
        tabFoodies.addItem(new ItemStack(ItemListener.MUTTON_COOKED));
        tabFoodies.addItem(new ItemStack(ItemListener.MUTTON_GLAZED));
        tabFoodies.addItem(new ItemStack(ItemListener.CALAMARI_RAW));
        tabFoodies.addItem(new ItemStack(ItemListener.CALAMARI_COOKED));
        tabFoodies.addItem(new ItemStack(ItemListener.CALAMARI_GLAZED));
        tabFoodies.addItem(new ItemStack(ItemListener.EGG_COOKED));
        tabFoodies.addItem(new ItemStack(ItemListener.PORK_GLAZED));
        tabFoodies.addItem(new ItemStack(ItemListener.COD_GLAZED));

        tabFoodies.addItem(new ItemStack(ItemListener.OMELET));
        tabFoodies.addItem(new ItemStack(ItemListener.FISHANDCHIPS));
        tabFoodies.addItem(new ItemStack(ItemListener.WHITECHEESE));
        tabFoodies.addItem(new ItemStack(ItemListener.STUFFED_CABBAGE));
        tabFoodies.addItem(new ItemStack(ItemListener.STUFFED_CHILI));
        tabFoodies.addItem(new ItemStack(ItemListener.BRUSCHETTA));

        tabFoodies.addItem(new ItemStack(ItemListener.STEW_CARROT));
        tabFoodies.addItem(new ItemStack(ItemListener.STEW_TOMATO));
        tabFoodies.addItem(new ItemStack(ItemListener.STEW_MEAT));
        tabFoodies.addItem(new ItemStack(ItemListener.STEW_FISH));
        tabFoodies.addItem(new ItemStack(ItemListener.STEW_ONION));

        tabFoodies.addItem(new ItemStack(ItemListener.PICO_DE_GALLO));
        tabFoodies.addItem(new ItemStack(ItemListener.SHAKSHOUKA));

        tabFoodies.addItem(new ItemStack(ItemListener.SANDWICH, 1, 1));

        tabFoodies.addItem(new ItemStack(ItemListener.PIE_CARROT));
        tabFoodies.addItem(new ItemStack(ItemListener.PIE_APPLE));

        tabFoodies.addItem(new ItemStack(ItemListener.JAR));
        tabFoodies.addItem(new ItemStack(ItemListener.JUICE_CACTUS));
        tabFoodies.addItem(new ItemStack(ItemListener.JUICE_APPLE));
        tabFoodies.addItem(new ItemStack(ItemListener.JUICE_TOMATO));

        tabFoodies.addItem(new ItemStack(ItemListener.JAR_BEES));
        tabFoodies.addItem(new ItemStack(ItemListener.HONEY));
        tabFoodies.addItem(new ItemStack(ItemListener.APPLE_GLAZED));
        tabFoodies.addItem(new ItemStack(ItemListener.COOKIE_HONEY));
        tabFoodies.addItem(new ItemStack(ItemListener.CANDY_HONEY));

        tabFoodies.addItem(new ItemStack(ItemListener.SALT));

        tabFoodies.addItem(new ItemStack(BlockListener.SALT_ORE));
        tabFoodies.addItem(new ItemStack(BlockListener.SALT_BLOCK));
        tabFoodies.addItem(new ItemStack(BlockListener.COOKING_STATION));

        tabFoodies.addItem(new ItemStack(BlockListener.CARROT_WILD));
        tabFoodies.addItem(new ItemStack(BlockListener.POTATO_WILD));
        tabFoodies.addItem(new ItemStack(BlockListener.ONION_WILD));
        tabFoodies.addItem(new ItemStack(BlockListener.TOMATO_WILD));
        tabFoodies.addItem(new ItemStack(BlockListener.CABBAGE_WILD));

        tabFoodies.addItem(new ItemStack(BlockListener.BEEHIVE_OAK));
        tabFoodies.addItem(new ItemStack(BlockListener.BEENEST_OAK));
        tabFoodies.addItem(new ItemStack(BlockListener.BEENEST_BIRCH));
        tabFoodies.addItem(new ItemStack(BlockListener.HONEYCOMB));
    }
}
