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
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.DirectionProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

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
}
