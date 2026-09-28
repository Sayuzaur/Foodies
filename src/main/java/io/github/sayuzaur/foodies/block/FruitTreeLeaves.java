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

package io.github.sayuzaur.foodies.block;

import com.google.common.primitives.Floats;
import farn.farn_util.api.particle.ParticleAPI;
import io.github.sayuzaur.foodies.events.init.BlockListener;
import io.github.sayuzaur.foodies.events.init.ItemListener;
import io.github.sayuzaur.foodies.particle.Petal;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.context.BlockTagContext;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.registry.tag.BlockTags;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class FruitTreeLeaves extends TemplateBlock {
    public static final IntProperty AGE;
    public static final IntProperty SUBTYPE;
    public static final BooleanProperty GROWTH_BLOCKED;
    public static final BooleanProperty PERSISTENT;

    static {
        AGE = Properties.AGE_7;
        SUBTYPE = IntProperty.of("fruit_subtype", 0, 7);
        GROWTH_BLOCKED = BooleanProperty.of("growth_blocked");
        PERSISTENT = BooleanProperty.of("persistent");
    }

    int[] decayRegion;

    public FruitTreeLeaves(Identifier identifier) {
        super(identifier, Material.LEAVES);
        this.setSoundGroup(DIRT_SOUND_GROUP);
        this.setHardness(0.2F);
        this.setOpacity(1);
        this.setTickRandomly(true);
        setDefaultState(getStateManager().getDefaultState().with(AGE, 0).with(SUBTYPE, 0).with(GROWTH_BLOCKED, false). with(PERSISTENT, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, SUBTYPE, GROWTH_BLOCKED, PERSISTENT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(AGE, 0).with(SUBTYPE, 0).with(GROWTH_BLOCKED, false).with(PERSISTENT, true);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side) {
        blockView.getBlockId(x, y, z);
        return true;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    public int getDroppedItemId(int blockMeta, Random random) {
        return ItemListener.CABBAGE_SEEDS.id;
    }

    public int getDroppedItemCount(Random random) {
        return 1;
    }

    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        if (!world.isRemote && playerEntity.getHand() != null && playerEntity.getHand().itemId == Item.SHEARS.id) {
            this.dropStack(world, x, y, z, new ItemStack(this, 1));
        } else {
            super.afterBreak(world, playerEntity, x, y, z, meta);
        }
    }

    public void onBreak(World world, int x, int y, int z) {
        byte var5 = 1;
        int var6 = var5 + 1;
        if (world.isRegionLoaded(x - var6, y - var6, z - var6, x + var6, y + var6, z + var6)) {
            for(int var7 = -var5; var7 <= var5; ++var7) {
                for(int var8 = -var5; var8 <= var5; ++var8) {
                    for(int var9 = -var5; var9 <= var5; ++var9) {
                        BlockState state = world.getBlockState(x + var7, y + var8, z + var9);
                        if (state.isIn(BlockTags.LEAVES, BlockTagContext.of(world, x + var7, y + var8, z + var9)) || state.getMaterial() == Material.LEAVES) {
                            int var11 = world.getBlockMeta(x + var7, y + var8, z + var9);
                            world.setBlockMetaWithoutNotifyingNeighbors(x + var7, y + var8, z + var9, var11 | 8);
                        }
                    }
                }
            }
        }
    }

    private void breakLeaves(World world, int x, int y, int z) {
        this.dropStacks(world, x, y, z, world.getBlockMeta(x, y, z));
        world.setBlock(x, y, z, 0);
    }

    public void onTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            //TODO Ignore if is persistent
            int meta = world.getBlockMeta(x, y, z);
            if ((meta & 8) != 0) {
                byte range = 4;
                int offset = range + 1;
                byte regionBase = 32;
                int regionSquared = regionBase * regionBase;
                int regionHalf = regionBase / 2;
                if (this.decayRegion == null) {
                    this.decayRegion = new int[regionBase * regionBase * regionBase];
                }

                if (world.isRegionLoaded(x - offset, y - offset, z - offset, x + offset, y + offset, z + offset)) {
                    for(int varX = -range; varX <= range; ++varX) {
                        for(int varY = -range; varY <= range; ++varY) {
                            for(int varZ = -range; varZ <= range; ++varZ) {
                                BlockState state = world.getBlockState(x + varX, y + varY, z + varZ);
                                if (state.isIn(BlockTags.LOGS, BlockTagContext.of(world, x + varX, y + varY, z + varZ))) {
                                    this.decayRegion[(varX + regionHalf) * regionSquared + (varY + regionHalf) * regionBase + varZ + regionHalf] = 0;
                                } else if (state.isIn(BlockTags.LEAVES, BlockTagContext.of(world, x + varX, y + varY, z + varZ)) || state.getMaterial() == Material.LEAVES) {
                                    this.decayRegion[(varX + regionHalf) * regionSquared + (varY + regionHalf) * regionBase + varZ + regionHalf] = -2;
                                } else {
                                    this.decayRegion[(varX + regionHalf) * regionSquared + (varY + regionHalf) * regionBase + varZ + regionHalf] = -1;
                                }
                            }
                        }
                    }

                    for(int i = 1; i <= 4; ++i) {
                        for(int var18 = -range; var18 <= range; ++var18) {
                            for(int var19 = -range; var19 <= range; ++var19) {
                                for(int var20 = -range; var20 <= range; ++var20) {
                                    if (this.decayRegion[(var18 + regionHalf) * regionSquared + (var19 + regionHalf) * regionBase + var20 + regionHalf] == i - 1) {
                                        if (this.decayRegion[(var18 + regionHalf - 1) * regionSquared + (var19 + regionHalf) * regionBase + var20 + regionHalf] == -2) {
                                            this.decayRegion[(var18 + regionHalf - 1) * regionSquared + (var19 + regionHalf) * regionBase + var20 + regionHalf] = i;
                                        }

                                        if (this.decayRegion[(var18 + regionHalf + 1) * regionSquared + (var19 + regionHalf) * regionBase + var20 + regionHalf] == -2) {
                                            this.decayRegion[(var18 + regionHalf + 1) * regionSquared + (var19 + regionHalf) * regionBase + var20 + regionHalf] = i;
                                        }

                                        if (this.decayRegion[(var18 + regionHalf) * regionSquared + (var19 + regionHalf - 1) * regionBase + var20 + regionHalf] == -2) {
                                            this.decayRegion[(var18 + regionHalf) * regionSquared + (var19 + regionHalf - 1) * regionBase + var20 + regionHalf] = i;
                                        }

                                        if (this.decayRegion[(var18 + regionHalf) * regionSquared + (var19 + regionHalf + 1) * regionBase + var20 + regionHalf] == -2) {
                                            this.decayRegion[(var18 + regionHalf) * regionSquared + (var19 + regionHalf + 1) * regionBase + var20 + regionHalf] = i;
                                        }

                                        if (this.decayRegion[(var18 + regionHalf) * regionSquared + (var19 + regionHalf) * regionBase + (var20 + regionHalf - 1)] == -2) {
                                            this.decayRegion[(var18 + regionHalf) * regionSquared + (var19 + regionHalf) * regionBase + (var20 + regionHalf - 1)] = i;
                                        }

                                        if (this.decayRegion[(var18 + regionHalf) * regionSquared + (var19 + regionHalf) * regionBase + var20 + regionHalf + 1] == -2) {
                                            this.decayRegion[(var18 + regionHalf) * regionSquared + (var19 + regionHalf) * regionBase + var20 + regionHalf + 1] = i;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int var17 = this.decayRegion[regionHalf * regionSquared + regionHalf * regionBase + regionHalf];
                if (var17 >= 0) {
                    world.setBlockMetaWithoutNotifyingNeighbors(x, y, z, meta & -9);
                } else {
                    this.breakLeaves(world, x, y, z);
                }
            }
        }
    }

    public void onSteppedOn(World world, int x, int y, int z, Entity entity) {
        super.onSteppedOn(world, x, y, z, entity);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (!world.isRemote) {
            BlockState current = world.getBlockState(x, y, z);
            int age = current.get(AGE);
            if (age == 7) {
                world.setBlockState(x, y, z, current.with(AGE, 0));
            } else {
                age++;
                world.setBlockState(x, y, z, current.with(AGE, age));
            }
            return true;
        }

        return true;
    }

    String getPetalName() {
        if (this == BlockListener.LEAVES_APPLE) {
            return "apple";
        }
        if (this == BlockListener.LEAVES_ORANGE) {
            return  "orange";
        }
        if (this == BlockListener.LEAVES_PEACH) {
            return  "peach";
        } else {
            return "";
        }
    }

    float velocityMaxRange = 0.1F;
    float velocityRainBoost = 1.7F;
    double windCycle = 30000;

    private float getVelocityX(World world) {
        long time = world.getTime();
        double angle1 = ((time % windCycle) / windCycle) * Math.PI * 2.0;
        double angle2 = ((time % (windCycle * 0.3)) / (windCycle * 0.3)) * Math.PI * 2.0;
        float velocity = MathHelper.sin((float) angle1) * 0.04F + MathHelper.sin((float) angle2) * 0.015F;
        float velocityClamped = Floats.constrainToRange(velocity, -velocityMaxRange, velocityMaxRange);
        if (world.isRaining()) {
            return velocityClamped * velocityRainBoost;
        } else {
            return velocityClamped;
        }
    }

    private float getVelocityZ(World world) {
        long time = world.getTime();
        double angle1 = ((time % windCycle) / windCycle) * Math.PI * 2.0;
        double angle2 = ((time % (windCycle * 0.6)) / (windCycle * 0.6)) * Math.PI * 2.0;
        float velocity = MathHelper.cos((float) angle1) * 0.04F + MathHelper.cos((float) angle2) * 0.015F;
        float velocityClamped = Floats.constrainToRange(velocity, -velocityMaxRange, velocityMaxRange);
        if (world.isRaining()) {
            return velocityClamped * velocityRainBoost;
        } else {
            return velocityClamped;
        }
    }

    private float getVelocityY(World world) {
        float velocity = -0.05F;
        if (world.isRaining()) {
            return velocity - (velocity * 0.3F);
        } else {
            return velocity;
        }
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if ((world.isAir(x, y - 1, z) || world.getMaterial(x, y - 1, z) == Material.PLANT) && random.nextInt(15) == 0) {
            ParticleAPI.addParticle(new Petal(world, x, y, z, getVelocityX(world), getVelocityY(world), getVelocityZ(world), getPetalName()));
        }
    }
}
