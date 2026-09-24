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

package io.github.sayuzaur.foodies.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.client.color.world.BiomeColors;
import net.modificationstation.stationapi.api.client.event.color.block.BlockColorsRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.util.math.ColorHelper;
import net.modificationstation.stationapi.api.util.math.MathHelper;

import java.lang.invoke.MethodHandles;

public class Colouriser {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    protected int getOrangeLeavesColour(BlockView world, BlockPos pos) {
        int ogColour = BiomeColors.getFoliageColor(world, pos);
        int blue =  MathHelper.clamp(ColorHelper.Argb.getBlue(ogColour)  - 25, 15, 255);
        int green = MathHelper.clamp(ColorHelper.Argb.getGreen(ogColour) - 15, 15, 255);
        int red =   MathHelper.clamp(ColorHelper.Argb.getRed(ogColour)   + 60, 15, 130);

        return ColorHelper.Argb.getArgb(255, red, green, blue);
    }

    protected int getBerryLeavesColour(BlockView world, BlockPos pos) {
        int ogColour = BiomeColors.getFoliageColor(world, pos);
        int blue =  MathHelper.clamp(ColorHelper.Argb.getBlue(ogColour)  + 35, 15, 255);
        int green = MathHelper.clamp(ColorHelper.Argb.getGreen(ogColour) + 35, 15, 255);
        int red =   MathHelper.clamp(ColorHelper.Argb.getRed(ogColour)   + 65, 15, 190);

        return ColorHelper.Argb.getArgb(255, red, green, blue);
    }

    @EventListener
    public void registerBlockColours(BlockColorsRegisterEvent event) {
        event.blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
            //I have no idea if 'assert' here is required, but IntelliJ shows warnings and it annoys me
            assert world != null;
            assert pos != null;
            return BiomeColors.getFoliageColor(world, pos);
            },  BlockListener.CARROT_WILD,
                BlockListener.CABBAGE_WILD,
                BlockListener.ONION_WILD,
                BlockListener.POTATO_WILD,
                BlockListener.TOMATO_WILD,
                BlockListener.LEAVES_APPLE,
                BlockListener.LEAVES_ORANGE,
                BlockListener.LEAVES_PEACH);

        event.blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
            assert world != null;
            assert pos != null;
            return getOrangeLeavesColour(world, pos);
            },  BlockListener.LEAVES_ORANGE);

        event.blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
            assert world != null;
            assert pos != null;
            return getBerryLeavesColour(world, pos);
        },  BlockListener.CLOUDBERRY_BUSH);
    }
}