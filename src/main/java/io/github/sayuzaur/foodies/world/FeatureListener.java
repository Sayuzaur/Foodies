package io.github.sayuzaur.foodies.world;

import io.github.sayuzaur.foodies.world.feature.SaltOreVein;
import io.github.sayuzaur.foodies.world.feature.WildCarrotPatchFeature;
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
