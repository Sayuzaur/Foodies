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
        cir.setReturnValue(random.nextInt(2) == 0 ? ItemListener.MYSTERY_SEEDS.id : -1);
    }
}
