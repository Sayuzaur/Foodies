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

package io.github.sayuzaur.foodies.block.plant;

import net.minecraft.block.Block;
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

public class Cloudberry extends BasePlant {
    public static final IntProperty AGE;
    public static final IntProperty SUBTYPE;
    public static final BooleanProperty SNOWLOGGED;
    public static final BooleanProperty GROWTH_BLOCKED;

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

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (!world.isRemote) {
            ItemStack userHand = player.getHand();
            BlockState current = world.getBlockState(x, y, z);
            if (userHand != null && (userHand.itemId == Item.SNOWBALL.id || userHand.itemId == Block.SNOW.id)) {
                boolean isSnowLogged = current.get(SNOWLOGGED);
                if (!isSnowLogged) {
                    world.setBlockState(x, y, z, current.with(SNOWLOGGED, true));
                    userHand.count--;
                }
            } else {
                int age = current.get(AGE);
                if (age == 5) {
                    world.setBlockState(x, y, z, current.with(AGE, 0));
                } else {
                    age++;
                    world.setBlockState(x, y, z, current.with(AGE, age));
                }
            }
            return true;
        }
        return false;
    }
}
