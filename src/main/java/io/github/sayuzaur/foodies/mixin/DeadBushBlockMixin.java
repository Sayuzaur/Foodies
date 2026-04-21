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

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(DeadBushBlock.class)
public class DeadBushBlockMixin {
    @Unique
    public Random random = new Random();

    @Inject(method = "getDroppedItemId", at = @At("HEAD"), cancellable = true)
    void getDroppedItemId(int blockMeta, Random random, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(random.nextInt(4) == 0 ? ItemListener.MYSTERY_SEEDS.id : -1);
    }
}
