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

import io.github.sayuzaur.foodies.item.Bottle;
import io.github.sayuzaur.foodies.item.Jar;
import io.github.sayuzaur.foodies.item.crops.*;
import io.github.sayuzaur.foodies.item.food.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.template.item.TemplateFoodItem;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;

import java.lang.invoke.MethodHandles;

import static io.github.sayuzaur.foodies.FoodiesMod.NAMESPACE;
import static io.github.sayuzaur.foodies.FoodiesMod.FOOD_CONFIG;

public class ItemListener {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    public static Item SALT;

    public static Item CARROT;

    public static Item POTATO;
    public static Item POTATO_BAKED;

    public static Item ONION;

    public static Item TOMATO;
    public static Item TOMATO_SEEDS;

    public static Item CABBAGE;
    public static Item CABBAGE_SEEDS;

    public static Item MYSTERY_SEEDS;

    public static Item CHILI;
    public static Item CHILI_SEEDS;

    public static Item BEEF_RAW;
    public static Item BEEF_COOKED;
    public static Item BEEF_GLAZED;
    public static Item CHICKEN_RAW;
    public static Item CHICKEN_COOKED;
    public static Item CHICKEN_GLAZED;
    public static Item CHICKEN_DRUMSTICK_RAW;
    public static Item CHICKEN_DRUMSTICK_COOKED;
    public static Item CHICKEN_DRUMSTICK_GLAZED;
    public static Item MUTTON_RAW;
    public static Item MUTTON_COOKED;
    public static Item MUTTON_GLAZED;
    public static Item CALAMARI_RAW;
    public static Item CALAMARI_COOKED;
    public static Item CALAMARI_GLAZED;
    public static Item EGG_COOKED;
    public static Item PORK_GLAZED;
    public static Item COD_GLAZED;

    public static Item OMELET;
    public static Item FISHANDCHIPS;
    public static Item WHITECHEESE;
    public static Item STUFFED_CABBAGE;
    public static Item STUFFED_CHILI;
    public static Item BRUSCHETTA;

    public static Item STEW_CARROT;
    public static Item STEW_TOMATO;
    public static Item STEW_MEAT;
    public static Item STEW_FISH;
    public static Item STEW_ONION;

    public static Item PICO_DE_GALLO;
    public static Item SHAKSHOUKA;

    public static Item SANDWICH;

    public static Item BOTTLE;
    public static Item JUICE_CACTUS;
    public static Item JUICE_APPLE;
    public static Item JUICE_TOMATO;

    public static Item PIE_CARROT;
    public static Item PIE_APPLE;

    public static Item HONEY;
    public static Item JAR;
    public static Item JAR_BEES;
    public static Item APPLE_GLAZED;
    public static Item COOKIE_HONEY;
    public static Item CANDY_HONEY;

    static int honeyGlazedBonus = 1;

    @EventListener
    public static void registerItems(ItemRegistryEvent event) {
        SALT = new TemplateItem(NAMESPACE.id("salt"));

        CARROT = new Carrot(NAMESPACE.id("carrot"));

        POTATO = new Potato(NAMESPACE.id("potato"));
        POTATO_BAKED = new TemplateFoodItem(NAMESPACE.id("potato_baked"), 4, false );

        ONION = new Onion(NAMESPACE.id("onion"));

        TOMATO = new TemplateStackableFoodItem(NAMESPACE.id("tomato"), FOOD_CONFIG.rawHeal, false, FOOD_CONFIG.rawStackSize);
        TOMATO_SEEDS = new TomatoSeeds(NAMESPACE.id("tomato_seeds"));

        CABBAGE = new TemplateStackableFoodItem(NAMESPACE.id("cabbage"), FOOD_CONFIG.rawHeal, false, FOOD_CONFIG.rawStackSize);
        CABBAGE_SEEDS = new CabbageSeeds(NAMESPACE.id("cabbage_seeds"));

        MYSTERY_SEEDS = new MysterySeeds(NAMESPACE.id("mystery_seeds"));

        CHILI = new Chili(NAMESPACE.id("chili"));
        CHILI_SEEDS = new ChiliSeeds(NAMESPACE.id("chili_seeds"));

        BEEF_RAW = new TemplateFoodItem(NAMESPACE.id("beef_raw"), 3, true);
        BEEF_COOKED = new TemplateFoodItem(NAMESPACE.id("beef_cooked"), 8, true);
        BEEF_GLAZED = new TemplateFoodItem(NAMESPACE.id("beef_glazed"), 8 + honeyGlazedBonus, true);
        CHICKEN_RAW = new TemplateFoodItem(NAMESPACE.id("chicken_raw"), 2, true);
        CHICKEN_COOKED = new TemplateFoodItem(NAMESPACE.id("chicken_cooked"), 6, true);
        CHICKEN_GLAZED = new TemplateFoodItem(NAMESPACE.id("chicken_glazed"), 6 + honeyGlazedBonus, true);
        CHICKEN_DRUMSTICK_RAW = new TemplateStackableFoodItem(NAMESPACE.id("chicken_drumstick_raw"), 1, true, 2);
        CHICKEN_DRUMSTICK_COOKED = new TemplateStackableFoodItem(NAMESPACE.id("chicken_drumstick_cooked"), 3, true, 2);
        CHICKEN_DRUMSTICK_GLAZED = new TemplateStackableFoodItem(NAMESPACE.id("chicken_drumstick_glazed"), 3 + honeyGlazedBonus, true, 2);
        MUTTON_RAW = new TemplateFoodItem(NAMESPACE.id("mutton_raw"), 2, true);
        MUTTON_COOKED = new TemplateFoodItem(NAMESPACE.id("mutton_cooked"), 7, true);
        MUTTON_GLAZED = new TemplateFoodItem(NAMESPACE.id("mutton_glazed"), 7 + honeyGlazedBonus, true);
        CALAMARI_RAW = new TemplateStackableFoodItem(NAMESPACE.id("calamari_raw"), 3, true, 2);
        CALAMARI_COOKED = new TemplateStackableFoodItem(NAMESPACE.id("calamari_cooked"), 6, true, 2);
        CALAMARI_GLAZED = new TemplateStackableFoodItem(NAMESPACE.id("calamari_glazed"), 6 + honeyGlazedBonus, true, 2);
        EGG_COOKED = new TemplateFoodItem(NAMESPACE.id("egg_cooked"), 3, true);
        PORK_GLAZED = new TemplateFoodItem(NAMESPACE.id("pork_glazed"), 8 + honeyGlazedBonus, true);
        COD_GLAZED = new TemplateFoodItem(NAMESPACE.id("cod_glazed"), 5 + honeyGlazedBonus, true);

        OMELET = new TemplateFoodItem(NAMESPACE.id("omelet"), 6, false);
        FISHANDCHIPS = new TemplateFoodItem(NAMESPACE.id("fish_and_chips"), 8, false);
        WHITECHEESE = new TemplateFoodItem(NAMESPACE.id("white_cheese"), 6, false);
        STUFFED_CABBAGE = new TemplateStackableFoodItem(NAMESPACE.id("stuffed_cabbage"), 5, false, 2);
        STUFFED_CHILI = new TemplateStackableFoodItem(NAMESPACE.id("stuffed_chili"), 4, false, 2);
        BRUSCHETTA = new TemplateStackableFoodItem(NAMESPACE.id("bruschetta"), 6, false, 2);

        STEW_CARROT = new BaseStew(NAMESPACE.id("stew_carrot"), 8, false);
        STEW_TOMATO = new BaseStew(NAMESPACE.id("stew_tomato"), 7, false);
        STEW_MEAT = new BaseStew(NAMESPACE.id("stew_meat"), 11, false);
        STEW_FISH = new BaseStew(NAMESPACE.id("stew_fish"), 8, false);
        STEW_ONION = new BaseStew(NAMESPACE.id("stew_onion"), 10, false);

        PICO_DE_GALLO = new BaseStew(NAMESPACE.id("pico_de_gallo"), 8, false);
        SHAKSHOUKA = new BaseStew(NAMESPACE.id("shakshouka"), 8, false);

        SANDWICH = new Sandwich(NAMESPACE.id("sandwich"));

        PIE_CARROT = new TemplateFoodItem(NAMESPACE.id("pie_carrot"), 10, false);
        PIE_APPLE = new TemplateFoodItem(NAMESPACE.id("pie_apple"), 10, false);

        BOTTLE = new Bottle(NAMESPACE.id("bottle"));
        JAR = new Jar(NAMESPACE.id("jar"));
        JUICE_CACTUS = new BaseJuice(NAMESPACE.id("juice_cactus"), 3);
        JUICE_APPLE = new BaseJuice(NAMESPACE.id("juice_apple"), 3);
        JUICE_TOMATO = new BaseJuice(NAMESPACE.id("juice_tomato"), 3);

        JAR_BEES = new TemplateItem(NAMESPACE.id("jar_bees")).setMaxCount(1).setCraftingReturnItem(ItemListener.JAR);
        HONEY = new TemplateItem(NAMESPACE.id("honey"));
        APPLE_GLAZED = new TemplateFoodItem(NAMESPACE.id("apple_glazed"), 4 + honeyGlazedBonus, false);
        COOKIE_HONEY = new TemplateStackableFoodItem(NAMESPACE.id("cookie_honey"), 1, false, 8);
        CANDY_HONEY = new TemplateStackableFoodItem(NAMESPACE.id("candy_honey"), 1, false, 8);
    }
}
