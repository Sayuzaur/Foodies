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

package io.github.sayuzaur.foodies.mixin;

import io.github.sayuzaur.foodies.block.crops.*;
import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.sayuzaur.foodies.block.crops.BaseCrops.AGE;
import static io.github.sayuzaur.foodies.block.crops.RegrowingCrops.AGE10;


@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    void useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side, CallbackInfoReturnable<Boolean> cir){
        if (stack.getDamage() == 15){
            int blockId = world.getBlockId(x, y, z);

            if (blockId == BlockListener.CARROT_CROPS.id) {
                if (!world.isRemote){
                    BlockState state = world.getBlockState(x, y, z);
                    int age = state.get(AGE);

                    if (age < 7){
                        ((CarrotCrops) BlockListener.CARROT_CROPS).applyFullGrowth(world, x, y, z);
                        --stack.count;
                    }
                }
                cir.setReturnValue(true);
            } else if (blockId == BlockListener.POTATO_CROPS.id) {
                if (!world.isRemote){
                    BlockState state = world.getBlockState(x, y, z);
                    int age = state.get(AGE);

                    if (age < 7){
                        ((PotatoCrops) BlockListener.POTATO_CROPS).applyFullGrowth(world, x, y, z);
                        --stack.count;
                    }
                }
                cir.setReturnValue(true);
            } else if (blockId == BlockListener.ONION_CROPS.id) {
                if (!world.isRemote){
                    BlockState state = world.getBlockState(x, y, z);
                    int age = state.get(AGE);

                    if (age < 7){
                        ((OnionCrops) BlockListener.ONION_CROPS).applyFullGrowth(world, x, y, z);
                        --stack.count;
                    }
                }
                cir.setReturnValue(true);
            } else if (blockId == BlockListener.CABBAGE_CROPS.id) {
                if (!world.isRemote){
                    BlockState state = world.getBlockState(x, y, z);
                    int age = state.get(AGE);

                    if (age < 7){
                        ((CabbageCrops) BlockListener.CABBAGE_CROPS).applyFullGrowth(world, x, y, z);
                        --stack.count;
                    }
                }
                cir.setReturnValue(true);
            } else if (blockId == BlockListener.TOMATO_CROPS.id) {
                if (!world.isRemote){
                    BlockState state = world.getBlockState(x, y, z);
                    int age = state.get(AGE10);

                    if (age < 10){
                        ((TomatoCrops) BlockListener.TOMATO_CROPS).applyFullGrowth(world, x, y, z);
                        --stack.count;
                    }
                }
                cir.setReturnValue(true);
            }
        }
    }
}
