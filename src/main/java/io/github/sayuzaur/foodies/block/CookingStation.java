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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.DirectionProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Random;

public class CookingStation extends TemplateBlock {
    public static final DirectionProperty HORIZONTAL_FACING;
    public static final BooleanProperty IS_LIT;

    static {
        HORIZONTAL_FACING = Properties.FACING;
        IS_LIT = Properties.LIT;
    }

    public CookingStation(Identifier identifier){
        super(identifier, Material.STONE);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setSoundGroup(STONE_SOUND_GROUP);
        this.setLuminance(0.0F);
        setDefaultState(getStateManager().getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(IS_LIT, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        builder.add(IS_LIT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(HORIZONTAL_FACING,context.getHorizontalPlayerFacing()).with(IS_LIT, false);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (!world.isRemote) {
            BlockState current = world.getBlockState(x, y, z);
            boolean lit = current.get(IS_LIT);
            if (lit) {
                world.setBlockStateWithNotify(x, y, z, current.with(IS_LIT, false));
            } else {
                world.setBlockStateWithNotify(x, y, z, current.with(IS_LIT, true));
            }
            return true;
        }
        return false;
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        BlockState current = world.getBlockState(x, y, z);
        boolean lit = current.get(IS_LIT);
        Direction direction = current.get(HORIZONTAL_FACING);
        String face = direction.toString();

        if (lit) {
            float var7 = (float)x + 0.5F;
            float var8 = (float)y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float var9 = (float)z + 0.5F;
            float var10 = 0.52F;
            float var11 = random.nextFloat() * 0.6F - 0.3F;

            switch (face) {
                case "south" -> {
                    world.addParticle("smoke", (double) (var7 - var10), (double) var8, (double) (var9 + var11), (double) 0.0F, (double) 0.0F, (double) 0.0F);
                    world.addParticle("flame", (double) (var7 - var10), (double) var8, (double) (var9 + var11), (double) 0.0F, (double) 0.0F, (double) 0.0F);
                }
                case "north" -> {
                    world.addParticle("smoke", (double) (var7 + var10), (double) var8, (double) (var9 + var11), (double) 0.0F, (double) 0.0F, (double) 0.0F);
                    world.addParticle("flame", (double) (var7 + var10), (double) var8, (double) (var9 + var11), (double) 0.0F, (double) 0.0F, (double) 0.0F);
                }
                case "west" -> {
                    world.addParticle("smoke", (double) (var7 + var11), (double) var8, (double) (var9 - var10), (double) 0.0F, (double) 0.0F, (double) 0.0F);
                    world.addParticle("flame", (double) (var7 + var11), (double) var8, (double) (var9 - var10), (double) 0.0F, (double) 0.0F, (double) 0.0F);
                }
                case "east" -> {
                    world.addParticle("smoke", (double) (var7 + var11), (double) var8, (double) (var9 + var10), (double) 0.0F, (double) 0.0F, (double) 0.0F);
                    world.addParticle("flame", (double) (var7 + var11), (double) var8, (double) (var9 + var10), (double) 0.0F, (double) 0.0F, (double) 0.0F);
                }
            }
        }
    }
}
