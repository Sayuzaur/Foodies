package io.github.sayuzaur.foodies.mixin;

import io.github.sayuzaur.foodies.block.crops.CarrotCrops;
import io.github.sayuzaur.foodies.block.crops.PotatoCrops;
import io.github.sayuzaur.foodies.block.crops.TomatoCrops;
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
