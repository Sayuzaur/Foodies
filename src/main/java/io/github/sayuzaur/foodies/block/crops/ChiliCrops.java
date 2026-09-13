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

package io.github.sayuzaur.foodies.block.crops;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

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
    public boolean canPlantOnTop(int id) {
        return id == Block.GRAVEL.id;
    }

    private boolean isLavaNearby(World world, int x, int y, int z) {
        for(int var1 = x - 4; var1 <= x + 4; ++var1) {
            for(int var2 = y; var2 <= y + 1; ++var2) {
                for(int var3 = z - 4; var3 <= z + 4; ++var3) {
                    if (world.getMaterial(var1, var2, var3) == Material.LAVA) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public float getAvailableMoisture(World world, int x, int y, int z) {
        float moisture = 1.0F;
        int sideZ1 = world.getBlockId(x, y, z - 1);
        int sideZ2 = world.getBlockId(x, y, z + 1);
        int sideX1 = world.getBlockId(x - 1, y, z);
        int sideX2 = world.getBlockId(x + 1, y, z);
        int sideXZ1 = world.getBlockId(x - 1, y, z - 1);
        int sideXZ2 = world.getBlockId(x + 1, y, z - 1);
        int sideXZ3 = world.getBlockId(x + 1, y, z + 1);
        int sideXZ4 = world.getBlockId(x - 1, y, z + 1);
        boolean checkSidesX = sideX1 == this.id || sideX2 == this.id;
        boolean checkSidesZ = sideZ1 == this.id || sideZ2 == this.id;
        boolean checkSidesXZ = sideXZ1 == this.id || sideXZ2 == this.id || sideXZ3 == this.id || sideXZ4 == this.id;

        for(int checkX = x - 1; checkX <= x + 1; ++checkX) {
            for(int checkZ = z - 1; checkZ <= z + 1; ++checkZ) {
                int checkY = world.getBlockId(checkX, y - 1, checkZ);
                float addMoisture = 0.0F;
                if (checkY == Block.GRAVEL.id) {
                    addMoisture = 1.0F;
                    if (isLavaNearby(world, checkX, y - 1, checkZ)) {
                        addMoisture = 3.0F;
                    }
                }

                if (checkX != x || checkZ != z) {
                    addMoisture /= 4.0F;
                }

                moisture += addMoisture;
            }
        }

        if (checkSidesXZ || checkSidesX && checkSidesZ) {
            moisture /= 2.0F;
        }
        if (!isLavaNearby(world, x, y - 1, z)) {
            moisture = 0.0F;
        }

        return moisture;
    }
}
