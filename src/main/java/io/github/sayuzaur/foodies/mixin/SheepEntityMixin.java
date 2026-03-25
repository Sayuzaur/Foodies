package io.github.sayuzaur.foodies.mixin;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SheepEntity.class)
public class SheepEntityMixin {
    @Unique
    public Random random = new Random();

    @Shadow
    public int getColor() {
        return 0;
    }

    @Inject(method = "getDroppedItemId", at = @At("HEAD"), cancellable = true)
    protected void getDroppedItemId(CallbackInfoReturnable<Integer> cir) {
        cir.cancel();
    }

    @Inject(method = "dropItems", at = @At("HEAD"), cancellable = true)
    private void dropItems(CallbackInfo cir) {
        SheepEntity self = (SheepEntity)(Object)this;

        if (!self.isSheared()) {
            self.dropItem(new ItemStack(Block.WOOL.id, 1, this.getColor()), 0.0F);
        }

        if (random.nextInt(3) <= 1) {
            if (self.fireTicks > 0) {
                self.dropItem(ItemListener.MUTTON_COOKED.id, 1);
            } else {
                self.dropItem(ItemListener.MUTTON_RAW.id, 1);
            }
        }
        cir.cancel();
    }
}
