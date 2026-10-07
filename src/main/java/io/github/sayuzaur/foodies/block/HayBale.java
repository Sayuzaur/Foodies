/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public class HayBale extends TemplateBlock {
    public static final EnumProperty<Direction.Axis> AXIS;

    static {
        AXIS = Properties.AXIS;
    }

    public HayBale(Identifier identifier) {
        super(identifier, Material.SOLID_ORGANIC);
        this.setSoundGroup(DIRT_SOUND_GROUP);
        this.setHardness(0.5F);
        this.setResistance(0.5F);
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

    //TODO Add hay particles
//    public void onSteppedOn(World world, int x, int y, int z, Entity entity) {
//    }
}
