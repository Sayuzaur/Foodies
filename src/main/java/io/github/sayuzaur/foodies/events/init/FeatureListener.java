/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.events.init;

import io.github.sayuzaur.foodies.world.feature.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.worldgen.feature.HeightScatterFeature;
import net.modificationstation.stationapi.api.worldgen.feature.VolumetricScatterFeature;
import net.modificationstation.stationapi.api.worldgen.feature.WeightedFeature;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

public class FeatureListener {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    private static final Feature WILD_CARROT_PATCH =  new WeightedFeature(new HeightScatterFeature(new WildCropsPatchFeature(6, 4, 1),1), 48);
    private static final Feature WILD_POTATO_PATCH =  new WeightedFeature(new HeightScatterFeature(new WildCropsPatchFeature(6, 3, 2),1), 32);
    private static final Feature WILD_ONION_PATCH =   new WeightedFeature(new HeightScatterFeature(new WildCropsPatchFeature(7, 5, 3),1), 32);
    private static final Feature WILD_TOMATO_PATCH =  new WeightedFeature(new HeightScatterFeature(new WildCropsPatchFeature(5, 3, 4),1), 48);
    private static final Feature WILD_CABBAGE_PATCH = new WeightedFeature(new HeightScatterFeature(new WildCropsPatchFeature(6, 4, 5),1), 32);

    private static final Feature SALT_ORE = new VolumetricScatterFeature(new SaltOreVein(),1, 16, 32);


    @EventListener
    public void registerFeatures(BiomeModificationEvent event) {
        if (event.world.dimension.id == 0) {
            if (Objects.equals(event.biome.name, "Rainforest")) {
                event.biome.addFeature(WILD_CABBAGE_PATCH);
            }
            if (Objects.equals(event.biome.name, "Seasonal Forest")) {
                event.biome.addFeature(WILD_CABBAGE_PATCH);
            }
            if (Objects.equals(event.biome.name, "Plains")) {
                event.biome.addFeature(WILD_CARROT_PATCH);
            }
            if (Objects.equals(event.biome.name, "Forest")) {
                event.biome.addFeature(WILD_CARROT_PATCH);
                event.biome.addFeature(WILD_ONION_PATCH);
            }
            if (Objects.equals(event.biome.name, "Taiga")) {
                event.biome.addFeature(WILD_POTATO_PATCH);
            }
            if (Objects.equals(event.biome.name, "Tundra")) {
                event.biome.addFeature(WILD_POTATO_PATCH);
            }
            if (Objects.equals(event.biome.name, "Shrubland")) {
                event.biome.addFeature(WILD_TOMATO_PATCH);
            }
            if (Objects.equals(event.biome.name, "Savanna")) {
                event.biome.addFeature(WILD_TOMATO_PATCH);
            }

            //TODO I'm too lazy to rewrite this now. If you're reading this, please remind me about it.
            if (BirchTreeBeeFeature.targetBiomes.contains(event.biome.name)) {
                BirchTreeBeeFeature birchTreeBee = new BirchTreeBeeFeature();

                event.biome.addFeature(birchTreeBee);
            }
            if (OakTreeBeeFeature.targetBiomes.contains(event.biome.name)) {
                OakTreeBeeFeature oakTreeBee = new OakTreeBeeFeature();

                event.biome.addFeature(oakTreeBee);
            }

            event.biome.addFeature(SALT_ORE);
        }
    }
}
