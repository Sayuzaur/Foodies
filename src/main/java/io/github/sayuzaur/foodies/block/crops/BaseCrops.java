/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block.crops;

import io.github.sayuzaur.foodies.block.PlantLogic;
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
    public static final IntProperty SUBTYPE;
    public static final int MAX_AGE = 7;

    static {
        AGE = Properties.AGE_7;
        SUBTYPE = IntProperty.of("crop_subtype", 0, 7);
    }

    public BaseCrops(Identifier identifier){
        super(identifier, Material.PLANT);
        this.setTickRandomly(true);
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
        this.setSoundGroup(DIRT_SOUND_GROUP);
        setDefaultState(getStateManager().getDefaultState().with(AGE, 0).with(SUBTYPE, 0));
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
        builder.add(AGE, SUBTYPE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(AGE, 0).with(SUBTYPE, 0);
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

    protected boolean canPlantOnTop(World world, int x, int y, int z) {
        return PlantLogic.plantOnFarmland(world, x, y, z);
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return super.canPlaceAt(world, x, y, z)
                && this.canPlantOnTop(world, x, y - 1, z);
    }

    @Override
    public boolean canGrow(World world, int x, int y, int z) {
        return    (world.getBrightness(x, y, z) >= PlantLogic.defaultLightLevel()
                || world.hasSkyLight(x, y, z))
                && this.canPlantOnTop(world, x, y - 1, z);
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
        world.setBlockState(x, y, z, current.with(AGE, MAX_AGE));
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (world.getLightLevel(x, y + 1, z) >= PlantLogic.defaultLightLevel()) {
            BlockState state = world.getBlockState(x, y, z);
            int age = state.get(AGE);

            if (age < MAX_AGE) {
                if (PlantLogic.growAttemptVanilla(world, x, y, z, random)) {
                    ++age;
                    world.setBlockState(x, y, z, state.with(AGE, age));
                }
            }
        }
        this.breakIfCannotGrow(world, x, y, z);
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        ArrayList<ItemStack> drops = new ArrayList<>();

        //Always drop 1 seedItem, no matter the AGE
        drops.add(new ItemStack(getSeedItem()));

        if (state.get(AGE) == MAX_AGE) {
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

    @Override
    public boolean onBonemealUse(World world, int x, int y, int z, BlockState state) {
        if (!world.isRemote) {
            if (state.get(AGE) == MAX_AGE) {
                return false;
            } else {
                applyFullGrowth(world, x, y, z);
            }
        }
        PlantLogic.bonemealClientEffect(world, x, y, z);
        return true;
    }
}
