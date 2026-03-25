package io.github.sayuzaur.foodies.mixin;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SquidEntity.class)
public class SquidEntityMixin {
    @Unique
    public Random random = new Random();

    @Inject(method = "dropItems", at = @At("HEAD"), cancellable = true)
    private void dropItems(CallbackInfo cir) {
        SquidEntity self = (SquidEntity) (Object)this;

        int inkCount = this.random.nextInt(3) + 1;
        for (int i = 0; i < inkCount; i++) {
            self.dropItem(new ItemStack(Item.DYE, 1, 0), 0.0F);
        }

        if (random.nextInt(3) <= 1) {
            int calamariCount = this.random.nextInt(2) + 1;
            for (int i = 0; i < calamariCount; ++i) {
                if (self.fireTicks > 0) {
                    self.dropItem(ItemListener.CALAMARI_COOKED.id, 1);
                } else {
                    self.dropItem(ItemListener.CALAMARI_RAW.id, 1);
                }
            }
        }
        cir.cancel();
    }
}
