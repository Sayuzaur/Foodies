package io.github.sayuzaur.foodies.events.init;

import io.github.sayuzaur.foodies.item.Bottle;
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

    public static Item CHILI;
    public static Item CHILI_SEEDS;

    public static Item BEEF_RAW;
    public static Item BEEF_COOKED;
    public static Item CHICKEN_RAW;
    public static Item CHICKEN_COOKED;
    public static Item CHICKEN_DRUMSTICK_RAW;
    public static Item CHICKEN_DRUMSTICK_COOKED;
    public static Item MUTTON_RAW;
    public static Item MUTTON_COOKED;
    public static Item EGG_COOKED;

    public static Item OMELET;
    public static Item FISHANDCHIPS;
    public static Item WHITECHEESE;

    public static Item CARROT_STEW;
    public static Item TOMATO_STEW;
    public static Item STEW_MEAT;
    public static Item STEW_FISH;
    public static Item STEW_ONION;

    public static Item PICO_DE_GALLO;

    public static Item SANDWICH;

    public static Item BOTTLE;
    public static Item JUICE_CACTUS;
    public static Item JUICE_APPLE;

    public static Item CARROT_PIE;
    public static Item PIE_APPLE;


    @EventListener
    public static void registerItems(ItemRegistryEvent event){
        SALT = new TemplateItem(NAMESPACE.id("salt")).setTranslationKey(NAMESPACE.id("salt"));

        CARROT = new Carrot(NAMESPACE.id("carrot")).setTranslationKey(NAMESPACE.id("carrot"));

        POTATO = new Potato(NAMESPACE.id("potato")).setTranslationKey(NAMESPACE.id("potato"));
        POTATO_BAKED = new PotatoBaked(NAMESPACE.id("potato_baked")).setTranslationKey(NAMESPACE.id("potato_baked"));

        ONION = new Onion(NAMESPACE.id("onion")).setTranslationKey(NAMESPACE.id("onion"));

        TOMATO = new Tomato(NAMESPACE.id("tomato")).setTranslationKey(NAMESPACE.id("tomato"));
        TOMATO_SEEDS = new TomatoSeeds(NAMESPACE.id("tomato_seeds")).setTranslationKey(NAMESPACE.id("tomato_seeds"));

        CABBAGE = new TemplateStackableFoodItem(NAMESPACE.id("cabbage"), 1, false, 64).setTranslationKey(NAMESPACE.id("cabbage"));
        CABBAGE_SEEDS = new CabbageSeeds(NAMESPACE.id("cabbage_seeds")).setTranslationKey(NAMESPACE.id("cabbage_seeds"));

        CHILI = new Chili(NAMESPACE.id("chili")).setTranslationKey(NAMESPACE.id("chili"));
        CHILI_SEEDS = new ChiliSeeds(NAMESPACE.id("chili_seeds")).setTranslationKey(NAMESPACE.id("chili_seeds"));

        BEEF_RAW = new TemplateFoodItem(NAMESPACE.id("beef_raw"), 3, true).setTranslationKey(NAMESPACE.id("beef_raw"));
        BEEF_COOKED = new TemplateFoodItem(NAMESPACE.id("beef_cooked"), 8, true).setTranslationKey(NAMESPACE.id("beef_cooked"));
        CHICKEN_RAW = new TemplateFoodItem(NAMESPACE.id("chicken_raw"), 2, true).setTranslationKey(NAMESPACE.id("chicken_raw"));
        CHICKEN_COOKED = new TemplateFoodItem(NAMESPACE.id("chicken_cooked"), 6, true).setTranslationKey(NAMESPACE.id("chicken_cooked"));
        CHICKEN_DRUMSTICK_RAW = new TemplateStackableFoodItem(NAMESPACE.id("chicken_drumstick_raw"), 1, true, 2).setTranslationKey(NAMESPACE.id("chicken_drumstick_raw"));
        CHICKEN_DRUMSTICK_COOKED = new TemplateStackableFoodItem(NAMESPACE.id("chicken_drumstick_cooked"), 3, true, 2).setTranslationKey(NAMESPACE.id("chicken_drumstick_cooked"));
        MUTTON_RAW = new TemplateFoodItem(NAMESPACE.id("mutton_raw"), 2, true).setTranslationKey(NAMESPACE.id("mutton_raw"));
        MUTTON_COOKED = new TemplateFoodItem(NAMESPACE.id("mutton_cooked"), 7, true).setTranslationKey(NAMESPACE.id("mutton_cooked"));
        EGG_COOKED = new TemplateFoodItem(NAMESPACE.id("egg_cooked"), 3, true).setTranslationKey(NAMESPACE.id("egg_cooked"));

        OMELET = new Omelet(NAMESPACE.id("omelet")).setTranslationKey(NAMESPACE.id("omelet"));
        FISHANDCHIPS = new TemplateFoodItem(NAMESPACE.id("fish_and_chips"), 8, false).setTranslationKey(NAMESPACE.id("fish_and_chips"));
        WHITECHEESE = new TemplateFoodItem(NAMESPACE.id("white_cheese"), 6, false).setTranslationKey(NAMESPACE.id("white_cheese"));

        CARROT_STEW = new BaseStew(NAMESPACE.id("carrot_stew"), 8, false).setTranslationKey(NAMESPACE.id("carrot_stew"));
        TOMATO_STEW = new BaseStew(NAMESPACE.id("tomato_stew"), 7, false).setTranslationKey(NAMESPACE.id("tomato_stew"));
        STEW_MEAT = new BaseStew(NAMESPACE.id("stew_meat"), 11, false).setTranslationKey(NAMESPACE.id("stew_meat"));
        STEW_FISH = new BaseStew(NAMESPACE.id("stew_fish"), 8, false).setTranslationKey(NAMESPACE.id("stew_fish"));
        STEW_ONION = new BaseStew(NAMESPACE.id("stew_onion"), 10, false).setTranslationKey(NAMESPACE.id("stew_onion"));

        PICO_DE_GALLO = new BaseStew(NAMESPACE.id("pico_de_gallo"), 8, false).setTranslationKey(NAMESPACE.id("pico_de_gallo"));

        SANDWICH = new Sandwich(NAMESPACE.id("sandwich")).setTranslationKey(NAMESPACE.id("sandwich"));

        BOTTLE = new Bottle(NAMESPACE.id("bottle")).setTranslationKey(NAMESPACE.id("bottle"));
        JUICE_CACTUS = new BaseJuice(NAMESPACE.id("juice_cactus"), 3).setTranslationKey(NAMESPACE.id("juice_cactus"));
        JUICE_APPLE = new BaseJuice(NAMESPACE.id("juice_apple"), 3).setTranslationKey(NAMESPACE.id("juice_apple"));

        CARROT_PIE = new CarrotPie(NAMESPACE.id("pie_carrot")).setTranslationKey(NAMESPACE.id("pie_carrot"));
        PIE_APPLE = new TemplateFoodItem(NAMESPACE.id("pie_apple"), 10, false).setTranslationKey(NAMESPACE.id("pie_apple"));
    }
}
