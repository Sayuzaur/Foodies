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

package io.github.sayuzaur.foodies.world;

import io.github.sayuzaur.foodies.world.feature.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.event.world.gen.WorldGenEvent;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;

import java.lang.invoke.MethodHandles;
import java.util.Random;

public class FeatureListener {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    @EventListener
    public void registerFeatures(BiomeModificationEvent event){
        if (WildCarrotPatchFeature.targetBiomes.contains(event.biome.name)){
            WildCarrotPatchFeature carrotFeature = new WildCarrotPatchFeature();

            event.biome.addFeature(carrotFeature);
        }
        if (WildPotatoPatchFeature.targetBiomes.contains(event.biome.name)){
            WildPotatoPatchFeature potatoFeature = new WildPotatoPatchFeature();

            event.biome.addFeature(potatoFeature);
        }
        if (WildOnionPatchFeature.targetBiomes.contains(event.biome.name)){
            WildOnionPatchFeature onionFeature = new WildOnionPatchFeature();

            event.biome.addFeature(onionFeature);
        }
        if (WildTomatoPatchFeature.targetBiomes.contains(event.biome.name)){
            WildTomatoPatchFeature tomatoFeature = new WildTomatoPatchFeature();

            event.biome.addFeature(tomatoFeature);
        }
        if (WildCabbagePatchFeature.targetBiomes.contains(event.biome.name)){
            WildCabbagePatchFeature cabbageFeature = new WildCabbagePatchFeature();

            event.biome.addFeature(cabbageFeature);
        }
    }

    @EventListener
    public void populate(WorldGenEvent.ChunkDecoration event){
        Random random = event.random;
        World world = event.world;
        Biome biome = event.biome;

            int varX = event.x + event.random.nextInt(16);
            int varY = event.random.nextInt(32);
            int varZ = event.z + event.random.nextInt(16);

            new SaltOreVein().generate(event.world, event.random, varX, varY, varZ);
    }
}
