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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BaseCrops extends TemplateBlock {
    public static final IntProperty AGE;
    static {
        AGE = Properties.AGE_7;
    }

    public BaseCrops(Identifier identifier){
        super(identifier, Material.PLANT);
        this.setTickRandomly(true);
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
        this.setSoundGroup(DIRT_SOUND_GROUP);
        setDefaultState(getStateManager().getDefaultState().with(AGE, 0));
    }

    protected abstract Item getSeedItem();

    protected abstract int getBonusSeedCount();

    protected abstract int getBonusSeedChance();

    protected abstract Item getCropItem();

    protected abstract int getCropCount();

    protected abstract int getBonusCropCount();

    protected abstract int getBonusCropChance();

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(AGE, 0);
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    protected boolean canPlantOnTop(int id) {
        return id == Block.FARMLAND.id;
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return super.canPlaceAt(world, x, y, z)
                && this.canPlantOnTop(world.getBlockId(x, y - 1, z));
    }

    @Override
    public boolean canGrow(World world, int x, int y, int z) {
        return    (world.getBrightness(x, y, z) >= 8
                || world.hasSkyLight(x, y, z))
                && this.canPlantOnTop(world.getBlockId(x, y - 1, z));
    }

    protected final void breakIfCannotGrow(World world, int x, int y, int z) {
        if (!this.canGrow(world, x, y, z)) {
            this.dropStacks(world, x, y, z, world.getBlockMeta(x, y, z));
            world.setBlock(x, y, z, 0);
        }
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);
        this.breakIfCannotGrow(world, x, y, z);
    }

    public void applyFullGrowth(World world, int x, int y, int z) {
        BlockState current = world.getBlockState(x, y, z);
        world.setBlockState(x, y, z, current.with(AGE, 7));
    }

    public void bonemealClientsideEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, "step.grass", 1.0F, 1.6F);
    }

    @Override
    public boolean onBonemealUse(World world, int x, int y, int z, BlockState state) {
        if (!world.isRemote) {
            if (state.get(AGE) == 7) {
                return false;
            } else {
                applyFullGrowth(world, x, y, z);
            }
        }
        bonemealClientsideEffect(world, x, y, z);
        return true;
    }

    protected float getAvailableMoisture(World world, int x, int y, int z) {
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
                if (checkY == Block.FARMLAND.id) {
                    addMoisture = 1.0F;
                    if (world.getBlockMeta(checkX, y - 1, checkZ) > 0) {
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

        return moisture;
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        this.breakIfCannotGrow(world, x, y, z);

        if (world.getLightLevel(x, y + 1, z) >= 9) {
            BlockState state = world.getBlockState(x, y, z);
            int age = state.get(AGE);

            if (age < 7) {
                float moisture = this.getAvailableMoisture(world, x, y, z);
                if (random.nextInt((int)(100.0F / moisture)) == 0) {
                    ++age;
                    world.setBlockState(x, y, z, state.with(AGE, age));
                }
            }
        }
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        ArrayList<ItemStack> drops = new ArrayList<>();

        //Always drop 1 seedItem, no matter the AGE
        drops.add(new ItemStack(getSeedItem()));

        if (state.get(AGE) == 7) {
            //Base crop drop if fully grown
            drops.add(new ItemStack(getCropItem(), getCropCount()));

            //Bonus crop drop
            for(int i = 0; i < getBonusCropCount(); ++i) {
                if (world.random.nextInt(10) + 1 <= getBonusCropChance()) {
                    drops.add(new ItemStack(getCropItem()));
                }
            }
            //Bonus seed drop
            for(int i = 0; i < getBonusSeedCount(); ++i) {
                if (world.random.nextInt(10) + 1 <= getBonusSeedChance()) {
                    drops.add(new ItemStack(getSeedItem()));
                }
            }
        }

        return drops;
    }
}
