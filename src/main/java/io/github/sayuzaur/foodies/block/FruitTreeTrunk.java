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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.context.BlockTagContext;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.registry.tag.BlockTags;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public class FruitTreeTrunk extends TemplateBlock {
    public static final EnumProperty<Direction.Axis> AXIS;

    static {
        AXIS = Properties.AXIS;
    }

    public FruitTreeTrunk(Identifier identifier) {
        super(identifier, Material.WOOD);
        this.setSoundGroup(WOOD_SOUND_GROUP);
        this.setHardness(1.0F);
        this.setResistance(2.0F);
        setDefaultState(getStateManager().getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(AXIS, context.getSide().getAxis());
    }

    public void onBreak(World world, int x, int y, int z) {
        byte range = 4;
        int offset = range + 1;
        if (world.isRegionLoaded(x - offset, y - offset, z - offset, x + offset, y + offset, z + offset)) {
            for(int varX = -range; varX <= range; ++varX) {
                for(int varY = -range; varY <= range; ++varY) {
                    for(int varZ = -range; varZ <= range; ++varZ) {
                        BlockState state = world.getBlockState(x + varX, y + varY, z + varZ);
                        if (state.isIn(BlockTags.LEAVES, BlockTagContext.of(world, x + varX, y + varY, z + varZ)) || state.getMaterial() == Material.LEAVES) {
                            int var11 = world.getBlockMeta(x + varX, y + varY, z + varZ);
                            if ((var11 & 8) == 0) {
                                world.setBlockMetaWithoutNotifyingNeighbors(x + varX, y + varY, z + varZ, var11 | 8);
                            }
                        }
                    }
                }
            }
        }
    }
}