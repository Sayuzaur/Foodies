/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block.plant;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.github.sayuzaur.foodies.FoodiesMod.CROPS_CONFIG;

public class Cloudberry extends BasePlant {
    public static final IntProperty AGE;
    public static final IntProperty SUBTYPE;
    public static final BooleanProperty SNOWLOGGED;
    public static final BooleanProperty GROWTH_BLOCKED;
    public static final int BASE_GROW_CHANCE = 16;
    public static final int MAX_AGE = 5;

    static {
        AGE = Properties.AGE_5;
        SUBTYPE = IntProperty.of("fruit_subtype", 0, 7);
        GROWTH_BLOCKED = BooleanProperty.of("growth_blocked");
        SNOWLOGGED = BooleanProperty.of("snowlogged");
    }

    public Cloudberry(Identifier identifier) {
        super(identifier);
        this.setTickRandomly(true);
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        setDefaultState(getStateManager().getDefaultState().with(AGE, 0).with(SUBTYPE, 0).with(GROWTH_BLOCKED, false).with(SNOWLOGGED, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, SUBTYPE, GROWTH_BLOCKED, SNOWLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(AGE, 0).with(SUBTYPE, 0).with(GROWTH_BLOCKED, false).with(SNOWLOGGED, false);
    }

    @Override
    public boolean canPlantOnTop(World world, int x, int y, int z) {
        return plantOnDirts(world, x, y, z);
    }

    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        if (!world.isRemote && playerEntity.getHand() != null && playerEntity.getHand().itemId == Item.SHEARS.id) {
            this.dropStack(world, x, y, z, new ItemStack(this.asItem()));
        } else {
            super.afterBreak(world, playerEntity, x, y, z, meta);
        }
    }

    public Item getCropItem() {
        return ItemListener.CLOUDBERRY;
    }

    public int getCropCount(Random random) {
        return random.nextInt(2) + 1;
    }

    @Override
    public List<ItemStack> getDropList(World world, int x, int y, int z, BlockState state, int meta) {
        Random random = world.random;
        ArrayList<ItemStack> drops = new ArrayList<>();
        if(state.get(AGE) == MAX_AGE) {
            drops.add(new ItemStack(getCropItem(), getCropCount(random)));
        }
//        if(state.get(SNOWLOGGED)) {
//            drops.add(new ItemStack(Item.SNOWBALL, 1));
//        }
        if(state.get(GROWTH_BLOCKED)) {
            drops.add(new ItemStack(getGrowthBlockItemId(), 1, 0));
        }
        return drops;
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (world.getLightLevel(x, y, z) >= CROPS_CONFIG.lightLevelRequired && world.getBlockId(x, y, z) == this.id) {
            BlockState state = world.getBlockState(x, y, z);
            if(!state.get(GROWTH_BLOCKED)) {
                int age = state.get(AGE);

                if (age < MAX_AGE) {
                    int finalGrowChance = BASE_GROW_CHANCE;

                    if (state.get(SNOWLOGGED)) {
                        finalGrowChance = finalGrowChance * 2;
                    }
                    if (random.nextInt(finalGrowChance) == 0) {
                        ++age;
                        world.setBlockState(x, y, z, state.with(AGE, age));
                    }
                }
            }
        }
        this.breakIfCannotGrow(world, x, y, z);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        ItemStack userHand = player.getHand();
        BlockState state = world.getBlockState(x, y, z);
        if (!world.isRemote) {
            if (userHand != null && (userHand.itemId == Item.SNOWBALL.id || userHand.itemId == Block.SNOW.id)) {
                boolean isSnowLogged = state.get(SNOWLOGGED);
                if (!isSnowLogged) {
                    world.setBlockState(x, y, z, state.with(SNOWLOGGED, true));
                    userHand.count--;

                    snowloggingClientsideEffect(world, x, y, z);
                    return true;
                } else {
                    return false;
                }
            } else if (userHand != null && userHand.itemId == getGrowthBlockItemId()) {
                if (!state.get(GROWTH_BLOCKED)) {
                    world.setBlockState(x, y, z, state.with(GROWTH_BLOCKED, true));
                    userHand.count--;

                    blockGrowthClientsideEffect(world, x, y, z);
                    return true;
                } else {
                    return false;
                }
            } else if (userHand == null) {
                int age = state.get(AGE);
                if (age == MAX_AGE) {
                    age = 0;
                    world.setBlockState(x, y, z, state.with(AGE, age));

                    harvestClientsideEffect(world, x, y, z);

                    for (int i = 0; i < getCropCount(world.random); ++i) {

                        float varBase = 0.7F;
                        float varX = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                        float varY = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                        float varZ = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;

                        ItemEntity cropsItemEntity = new ItemEntity(world, ((float) x + varX), ((float) y + varY), ((float) z + varZ), new ItemStack(getCropItem()));
                        cropsItemEntity.pickupDelay = 10;
                        world.spawnEntity(cropsItemEntity);
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        //Handles clientSideEffect when on server. I hate how it's done, couldn't think of better idea.
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            int age = state.get(AGE);
            if (userHand == null) {
                if (age == MAX_AGE) {
                    harvestClientsideEffect(world, x, y, z);
                }
            } else if (userHand.itemId == Item.DYE.id && userHand.getDamage() == 15) {
                if (age != MAX_AGE) {
                    bonemealClientsideEffect(world, x, y, z);
                }
            } else if (userHand.itemId == Item.SNOWBALL.id || userHand.itemId == Block.SNOW.id) {
                if (!state.get(SNOWLOGGED)) {
                    snowloggingClientsideEffect(world, x, y, z);
                }
            } else if (userHand.itemId == getGrowthBlockItemId()) {
                if (!state.get(GROWTH_BLOCKED)) {
                    blockGrowthClientsideEffect(world, x, y, z);
                }
            }
        }
        return true;
    }

    public void applyFullGrowth(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);
        world.setBlockState(x, y, z, state.with(AGE, MAX_AGE));
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
        //Somehow when onUse() is used & Overwritten, calling here clientsideEffect on server doesn't work.
        //onUse() probably Overrides onBonemealUse behaviour in some way, because in plants without onUse() it works here.
        //Have to move bonemealClientsideEffect to onUse().
        bonemealClientsideEffect(world, x, y, z);
        return true;
    }
}
