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

import farn.farn_util.api.particle.ParticleAPI;
import io.github.sayuzaur.foodies.FoodiesMod;
import io.github.sayuzaur.foodies.events.init.ItemListener;
import io.github.sayuzaur.foodies.particle.BeeFlyingAround;
import io.github.sayuzaur.foodies.particle.BeeFlyingIn;
import io.github.sayuzaur.foodies.particle.BeeFlyingOut;
import io.github.sayuzaur.foodies.particle.BeeOnFlower;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.DirectionProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Random;

import static io.github.sayuzaur.foodies.FoodiesMod.BEEHIVE_CLIENT_CONFIG;

public class BeeHive extends TemplateBlock {
    public static final DirectionProperty HORIZONTAL_FACING;
    public static final IntProperty HONEY_LEVEL;
    public static final IntProperty POPULATION_LEVEL;

    static {
        HORIZONTAL_FACING = Properties.FACING;
        HONEY_LEVEL = Properties.HONEY_LEVEL;
        POPULATION_LEVEL = IntProperty.of("population_level", 0, 7);
    }

    public BeeHive(Identifier identifier) {
        super(identifier, Material.WOOD);
        this.setSoundGroup(WOOD_SOUND_GROUP);
        this.setTickRandomly(true);
        this.setHardness(2.0F);
        setDefaultState(getStateManager().getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(HONEY_LEVEL, 0).with(POPULATION_LEVEL, 0));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, HONEY_LEVEL, POPULATION_LEVEL);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(HORIZONTAL_FACING,context.getHorizontalPlayerFacing()).with(HONEY_LEVEL, 0).with(POPULATION_LEVEL, 0);
    }

    int morningTime = 0;
    int afternoonTime = 5000;
    int eveningTime = 10000;
    int nightTime = 12000;
    int minLightLevel = 8;
    int honeyDropCount = 2;
    int maxFlowers = 16;

    private boolean isMorning(World world) {
        long timeOfDay = world.getTime() % 24000L;
        return timeOfDay > morningTime && timeOfDay < afternoonTime;
    }
    private boolean isAfternoon(World world) {
        long timeOfDay = world.getTime() % 24000L;
        return timeOfDay > afternoonTime && timeOfDay < eveningTime;
    }
    private boolean isEvening(World world) {
        long timeOfDay = world.getTime() % 24000L;
        return timeOfDay > eveningTime && timeOfDay < nightTime;
    }
    private boolean isDay(World world) {
        long timeOfDay = world.getTime() % 24000L;
        return timeOfDay > morningTime && timeOfDay < nightTime;
    }
    private boolean isBright(World world, int x, int y, int z) {
        return world.getLightLevel(x, y, z) >= minLightLevel;
    }

    private boolean isEntranceOpen(World world, int x, int y, int z) {
        return world.getMaterial(x, y, z) == Material.AIR || world.getMaterial(x, y, z) == Material.PLANT;
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (isDay(world)) {
            BlockState state = world.getBlockState(x, y, z);
            Direction direction = state.get(HORIZONTAL_FACING);
            String face = direction.toString();
            int varX = x;
            int varZ = z;
            switch (face) {
                case "south" -> varX = x - 1;
                case "north" -> varX = x + 1;
                case "west" -> varZ = z - 1;
                case "east" -> varZ = z + 1;
            }

            if (isBright(world, varX, y, varZ) && isEntranceOpen(world, varX, y, varZ)) {
                int flowersNearby = 0;
                for (int flowerX = x - 4; flowerX <= x + 4; ++flowerX) {
                    for (int flowerY = y - 2; flowerY <= y + 1; ++flowerY) {
                        for (int flowerZ = z - 4; flowerZ <= z + 4; ++flowerZ) {
                            BlockState maybeFlower = world.getBlockState(flowerX, flowerY, flowerZ);
                            if (maybeFlower.isIn(FoodiesMod.FLOWERS)) {
                                flowersNearby++;
                            }
                        }
                    }
                }
                if (flowersNearby > maxFlowers) {
                    flowersNearby = maxFlowers;
                }
                if (flowersNearby > 0) {
                    if (random.nextInt(100 / flowersNearby) == 0) {
                        int honey = state.get(HONEY_LEVEL);
                        int population = state.get(POPULATION_LEVEL);
                        if (population < 7) {
                            population++;
                            world.setBlockState(x, y, z, state.with(POPULATION_LEVEL, population));
                        } else if (honey < 5) {
                            honey++;
                            world.setBlockState(x, y, z, state.with(HONEY_LEVEL, honey));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (!world.isRemote) {
            ItemStack userHand = player.getHand();
            BlockState state = world.getBlockState(x, y, z);
            int honey = state.get(HONEY_LEVEL);
            int population = state.get(POPULATION_LEVEL);
            //HARVESTING HONEY
            if (userHand == null) {
                if (honey == 5) {
                    honey = 0;

                    ItemStack stack = new ItemStack(ItemListener.HONEY);
                    ItemEntity itemEntity = new ItemEntity(world, player.x, player.y, player.z, stack);
                    world.spawnEntity(itemEntity);
                    world.playSound(x, y, z, "mob.chickenplop", 0.5F, 0.6F);

                    world.setBlockState(x, y, z, state.with(HONEY_LEVEL, honey));

                    return true;
                } else {
                    return false;
                }
                //FILL WITH HONEY
            } else if (userHand.itemId == Item.SLIMEBALL.id) {
                if (honey < 5) {
                    honey++;
                    userHand.count--;
                    world.setBlockState(x, y, z, state.with(HONEY_LEVEL, honey));

                    return true;
                } else {
                    return false;
                }
                //CATCHING BEES
            } else if (userHand.itemId == ItemListener.JAR.id) {
                if (population == 7) {
                    population = 0;
                    userHand.count--;

                    ItemStack stack = new ItemStack(ItemListener.JAR_BEES);
                    ItemEntity itemEntity = new ItemEntity(world, player.x, player.y, player.z, stack);
                    world.spawnEntity(itemEntity);
                    world.playSound(x, y, z, "mob.chickenplop", 0.5F, 1.6F);

                    world.setBlockState(x, y, z, state.with(POPULATION_LEVEL, population));

                    return true;
                } else {
                    return false;
                }
                //FEED BEES - INCREASE POPULATION
            } else if (userHand.itemId == Item.SUGAR.id) {
                if (population < 7) {
                    population++;
                    userHand.count--;

                    world.setBlockState(x, y, z, state.with(POPULATION_LEVEL, population));
                } else {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck) {
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, BlockState state, int meta) {
        if (!world.isRemote && player.inventory.getSelectedItem() != null && player.inventory.getSelectedItem().getItem() instanceof AxeItem) {
            this.dropStack(world, x, y, z, new ItemStack(this));
            honeyDropCount = 0;
        }
        dropStacks(world, x, y, z, state);

        super.afterBreak(world, player, x, y, z, state, meta);
    }

    public void dropStacks(World world, int x, int y, int z, BlockState state) {
        if (!world.isRemote) {
            int honey = state.get(HONEY_LEVEL);
            if (honey == 5) {
                honeyDropCount++;
            }
            if (honeyDropCount != 0) {
                for (int i = 0; i < honeyDropCount; i++) {
                    float varBase = 0.7F;
                    float varX = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                    float varY = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                    float varZ = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;

                    ItemEntity honeyItemEntity = new ItemEntity(world,((float)x + varX),((float)y + varY),((float)z + varZ), new ItemStack(ItemListener.HONEY));
                    honeyItemEntity.pickupDelay = 10;
                    world.spawnEntity(honeyItemEntity);
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if (isDay(world)) {
            BlockState current = world.getBlockState(x, y, z);
            Direction direction = current.get(HORIZONTAL_FACING);
            String face = direction.toString();
            int population = current.get(POPULATION_LEVEL);

            float varX = x;
            float varZ = z;
            float varY = y + 0.3F;
            int varX2 = x;
            int varZ2 = z;
            float varX3 = x;
            float varZ3 = z;
            float velocityX = 0.0F;
            float velocityZ = 0.0F;
            boolean canSpawnBee = true;

            int rand;
            if (population == 7) {
                rand = random.nextInt(5 + BEEHIVE_CLIENT_CONFIG.beeParticlesNum);
            } else if (population == 6) {
                rand = random.nextInt(11 + BEEHIVE_CLIENT_CONFIG.beeParticlesNum);
            } else {
                rand = random.nextInt(15 + BEEHIVE_CLIENT_CONFIG.beeParticlesNum);
            }

            switch (face) {
                case "east" -> {
                    varX = varX - 0.5F;
                    varX2 = varX2 - 1;
                    varX3 = varX3 - 3;
                    velocityX = velocityX - 1;
                }
                case "west" -> {
                    varX = varX + 0.5F;
                    varX2 = varX2 + 1;
                    varX3 = varX3 + 3;
                    velocityX = velocityX + 1;
                }
                case "south" -> {
                    varZ = varZ - 0.5F;
                    varZ2 = varZ2 - 1;
                    varZ3 = varZ3 - 3;
                    velocityZ = velocityZ - 1;
                }
                case "north" -> {
                    varZ = varZ + 0.5F;
                    varZ2 = varZ2 + 1;
                    varZ3 = varZ3 + 3;
                    velocityZ = velocityX + 1;
                }
            }
            if (isBright(world, varX2, y, varZ2) && isEntranceOpen(world, varX2, y, varZ2)) {
                if (isMorning(world)) {
                    if (rand == 0) {
                        ParticleAPI.addParticle(new BeeFlyingAround(world, varX, varY, varZ, velocityX, 0, velocityZ));
                    } else if (rand <= 3) {
                        ParticleAPI.addParticle(new BeeFlyingOut(world, varX, varY, varZ, velocityX, 0, velocityZ));
                    }
                } else if (isAfternoon(world)) {
                    if (rand == 0) {
                        ParticleAPI.addParticle(new BeeFlyingAround(world, varX, varY, varZ, velocityX, 0, velocityZ));
                    } else if (rand == 1) {
                        ParticleAPI.addParticle(new BeeFlyingOut(world, varX, varY, varZ, velocityX, 0, velocityZ));
                    } else if (rand == 2 || rand == 3) {
                        ParticleAPI.addParticle(new BeeFlyingIn(world, varX3, varY, varZ3, velocityX, 0, velocityZ));
                    }
                } else if (isEvening(world)) {
                    if (rand <= 3) {
                        ParticleAPI.addParticle(new BeeFlyingIn(world, varX3, varY, varZ3, velocityX, 0, velocityZ));
                    }
                }
                if (BEEHIVE_CLIENT_CONFIG.beesOnFlowers && rand == 5) {
                    for(int flowerX = x - 4; flowerX <= x + 4; ++flowerX) {
                        for(int flowerY = y - 2; flowerY <= y + 1; ++flowerY) {
                            for(int flowerZ = z - 4; flowerZ <= z + 4; ++flowerZ) {
                                BlockState state = world.getBlockState(flowerX, flowerY, flowerZ);
                                if (state.isIn(FoodiesMod.FLOWERS)) {
                                    if (random.nextInt(16) == 0 && canSpawnBee) {
                                        ParticleAPI.addParticle(new BeeOnFlower(world, flowerX, flowerY, flowerZ, 0, 0, 0));
                                        canSpawnBee = false;
                                    }
                                }
                            }
                        }
                    }
                }
                if (BEEHIVE_CLIENT_CONFIG.beesSoundVolume > 0.0F) {
                    world.playSound(x, y, z, "foodies:beehive.bee_ambient", (random.nextFloat() * 0.3F + 0.3F) * BEEHIVE_CLIENT_CONFIG.beesSoundVolume, random.nextFloat() * 0.3F + 0.8F);
                    if (random.nextInt(15 + BEEHIVE_CLIENT_CONFIG.beeParticlesNum) == 0) {
                        world.playSound(x, y, z, "foodies:beehive.bee_buzz", (random.nextFloat() * 0.4F + 0.4F) * BEEHIVE_CLIENT_CONFIG.beesSoundVolume, random.nextFloat() * 0.3F + 0.8F);
                    }
                }
            }
        }
    }
}
