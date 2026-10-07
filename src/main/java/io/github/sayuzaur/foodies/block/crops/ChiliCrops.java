/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block.crops;

import io.github.sayuzaur.foodies.block.PlantLogic;
import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class ChiliCrops extends RegrowingCrops {
    public ChiliCrops(Identifier identifier) {
        super(identifier);
    }

    @Override
    protected Item getSeedItem() {
        return ItemListener.CHILI_SEEDS;
    }

    @Override
    protected int getBonusSeedCount() {
        return 0;
    }

    @Override
    protected int getBonusSeedChance() {
        return 0;
    }

    @Override
    protected Item getCropItem() {
        return ItemListener.CHILI;
    }

    @Override
    protected int getCropCount() {
        return 1;
    }

    @Override
    protected int getBonusCropCount() {
        return 2;
    }

    @Override
    protected int getBonusCropChance() {
        return 5;
    }

    @Override
    protected boolean canPlantOnTop(World world, int x, int y, int z) {
        return PlantLogic.plantOnGravel(world, x, y, z);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (world.getLightLevel(x, y + 1, z) >= PlantLogic.defaultLightLevel()) {
            BlockState state = world.getBlockState(x, y, z);
            int age = state.get(AGE);

            if (age < MAX_AGE) {
                if (PlantLogic.growAttemptHellish(world, x, y, z, random)) {
                    ++age;
                    world.setBlockState(x, y, z, state.with(AGE, age));
                }
            }
        }
        this.breakIfCannotGrow(world, x, y, z);
    }
}
