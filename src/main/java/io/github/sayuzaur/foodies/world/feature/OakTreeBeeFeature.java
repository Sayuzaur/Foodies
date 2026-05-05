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

package io.github.sayuzaur.foodies.world.feature;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.List;
import java.util.Random;

import static io.github.sayuzaur.foodies.FoodiesMod.GEN_CONFIG;
import static io.github.sayuzaur.foodies.block.BeeHive.HORIZONTAL_FACING;
import static io.github.sayuzaur.foodies.block.BeeHive.POPULATION_LEVEL;

public class OakTreeBeeFeature extends Feature {
    public static List<String> targetBiomes = List.of(
            "Forest",
            "Seasonal Forest",
            "Rainforest"
    );

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (GEN_CONFIG.oakTreeBeeChance > 1) {
            if (random.nextInt(GEN_CONFIG.oakTreeBeeChance) != 0){
                return false;
            }
        }
        x = x + random.nextInt(16) - 8;
        z = z + random.nextInt(16) - 8;
        int treeHeight = random.nextInt(3) + 4;
        int beeNestY;
        if (treeHeight <= 4) {
            beeNestY = 0;
        } else if (treeHeight == 5) {
            beeNestY = random.nextInt(2);
        } else {
            beeNestY = random.nextInt(2) + 1;
        }
        int facingRand = random.nextInt(4);
        boolean canContinue = true;

        if (y >= 1 && y + treeHeight + 1 <= 128) {
            for(int checkY = y; checkY <= y + 1 + treeHeight; ++checkY) {
                byte checkOffset = 1;
                if (checkY == y) {
                    checkOffset = 0;
                }

                if (checkY >= y + 1 + treeHeight - 2) {
                    checkOffset = 2;
                }

                for(int checkX = x - checkOffset; checkX <= x + checkOffset && canContinue; ++checkX) {
                    for(int checkZ = z - checkOffset; checkZ <= z + checkOffset && canContinue; ++checkZ) {
                        if (checkY >= 0 && checkY < 128) {
                            int checkFreeSpaceBlockId = world.getBlockId(checkX, checkY, checkZ);
                            if (checkFreeSpaceBlockId != 0 && checkFreeSpaceBlockId != Block.LEAVES.id) {
                                canContinue = false;
                            }
                        } else {
                            canContinue = false;
                        }
                    }
                }
            }

            if (!canContinue) {
                return false;
            } else {
                int treeBaseBlockId = world.getBlockId(x, y - 1, z);
                if ((treeBaseBlockId == Block.GRASS_BLOCK.id || treeBaseBlockId == Block.DIRT.id) && y < 128 - treeHeight - 1) {
                    world.setBlockWithoutNotifyingNeighbors(x, y - 1, z, Block.DIRT.id);

                    for(int leavesY = y - 3 + treeHeight; leavesY <= y + treeHeight; ++leavesY) {
                        int tempY = leavesY - (y + treeHeight);
                        int tempSides = 1 - tempY / 2;

                        for(int leavesX = x - tempSides; leavesX <= x + tempSides; ++leavesX) {
                            int tempX = leavesX - x;

                            for(int leavesZ = z - tempSides; leavesZ <= z + tempSides; ++leavesZ) {
                                int tempZ = leavesZ - z;
                                if ((Math.abs(tempX) != tempSides || Math.abs(tempZ) != tempSides || random.nextInt(2) != 0 && tempY != 0) && !Block.BLOCKS_OPAQUE[world.getBlockId(leavesX, leavesY, leavesZ)]) {
                                    world.setBlockWithoutNotifyingNeighbors(leavesX, leavesY, leavesZ, Block.LEAVES.id);
                                }
                            }
                        }
                    }

                    for(int logY = 0; logY < treeHeight; ++logY) {
                        if (logY == beeNestY) {
                            world.setBlockWithoutNotifyingNeighbors(x, y + logY, z, BlockListener.BEENEST_OAK.id);
                            BlockState state = world.getBlockState(x, y + logY, z);
                            switch (facingRand) {
                                case 0 -> world.setBlockStateWithNotify(x, y + logY, z, state.with(POPULATION_LEVEL, 7).with(HORIZONTAL_FACING, Direction.SOUTH));
                                case 1 -> world.setBlockStateWithNotify(x, y + logY, z, state.with(POPULATION_LEVEL, 7).with(HORIZONTAL_FACING, Direction.NORTH));
                                case 2 -> world.setBlockStateWithNotify(x, y + logY, z, state.with(POPULATION_LEVEL, 7).with(HORIZONTAL_FACING, Direction.WEST));
                                case 3 -> world.setBlockStateWithNotify(x, y + logY, z, state.with(POPULATION_LEVEL, 7).with(HORIZONTAL_FACING, Direction.EAST));
                            }
                        } else {
                            int logTargetBlockId = world.getBlockId(x, y + logY, z);
                            if (logTargetBlockId == 0 || logTargetBlockId == Block.LEAVES.id) {
                                world.setBlockWithoutNotifyingNeighbors(x, y + logY, z, Block.LOG.id);
                            }
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
