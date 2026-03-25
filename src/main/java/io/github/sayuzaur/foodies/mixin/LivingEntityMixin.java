package io.github.sayuzaur.foodies.mixin;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PigZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    public Random random = new Random();

    @Inject(method = "dropItems", at = @At("HEAD"), cancellable = true)
    private void dropItems(CallbackInfo cir) {
        if ((Object) this instanceof CowEntity self) {
            if (random.nextInt(3) <= 1) {
                int leatherCount = this.random.nextInt(2) + 1;
                for (int i = 0; i < leatherCount; ++i) {
                    self.dropItem(Item.LEATHER.id, 1);
                }
            }

            if (random.nextInt(3) <= 1) {
                if (self.fireTicks > 0) {
                    self.dropItem(ItemListener.BEEF_COOKED.id, 1);
                } else {
                    self.dropItem(ItemListener.BEEF_RAW.id, 1);
                }
            }
            cir.cancel();
        }
        if ((Object) this instanceof ChickenEntity self) {
            if (random.nextInt(3) <= 1) {
                int featherCount = this.random.nextInt(2) + 1;
                for (int i = 0; i < featherCount; ++i) {
                    self.dropItem(Item.FEATHER.id, 1);
                }
            }
            //0 = whole chicken, 1 = drumstick(s), 2 = nothin
            int randMeatDrop = this.random.nextInt(3);
            if (randMeatDrop == 0) {
                if (self.fireTicks > 0) {
                    self.dropItem(ItemListener.CHICKEN_COOKED.id, 1);
                } else {
                    self.dropItem(ItemListener.CHICKEN_RAW.id, 1);
                }
            } else if (randMeatDrop == 1) {
                int drumstickCount = this.random.nextInt(2) + 1;
                for (int i = 0; i < drumstickCount; ++i) {
                    if (self.fireTicks > 0) {
                        self.dropItem(ItemListener.CHICKEN_DRUMSTICK_COOKED.id, 1);
                    } else {
                        self.dropItem(ItemListener.CHICKEN_DRUMSTICK_RAW.id, 1);
                    }
                }
            }
            cir.cancel();
        }
        if ((Object) this instanceof PigZombieEntity self) {
            if (random.nextInt(3) <= 1) {
                self.dropItem(ItemListener.CHILI.id, 1);
            }

            if (random.nextInt(3) <= 1) {
                self.dropItem(Item.COOKED_PORKCHOP.id, 1);
            }
            cir.cancel();
        }
    }
}
