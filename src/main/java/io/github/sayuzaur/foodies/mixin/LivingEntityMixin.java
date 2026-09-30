/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.mixin;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PigZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static io.github.sayuzaur.foodies.FoodiesMod.MOB_DROPS_CONFIG;

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

            if (random.nextInt(100) + 1 <= MOB_DROPS_CONFIG.beefDropChance) {
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

            if (random.nextInt(100) + 1 <= MOB_DROPS_CONFIG.chickenDropChance) {
                //0 = whole chicken, 1 = drumstick(s)
                int randMeatDrop = this.random.nextInt(2);
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
            }
            cir.cancel();
        }
        if ((Object) this instanceof PigZombieEntity self) {
            if (random.nextInt(100) + 1 <= MOB_DROPS_CONFIG.chiliZombiePigDropChance) {
                self.dropItem(ItemListener.CHILI.id, 1);
            }

            if (random.nextInt(100) + 1 <= MOB_DROPS_CONFIG.porkchopZombiePigDropChance) {
                self.dropItem(Item.COOKED_PORKCHOP.id, 1);
            }
            cir.cancel();
        }
    }

    @ModifyVariable(method = "onLanding", at = @At("HEAD"), argsOnly = true)
    private float reduceFallDistance(float fallDistance) {
        LivingEntity self = (LivingEntity) (Object) this;

        int landedBlockId = self.world.getBlockId(MathHelper.floor(self.x), MathHelper.floor(self.y - (double)0.2F - (double)self.standingEyeHeight), MathHelper.floor(self.z));

        if (landedBlockId == BlockListener.HAY_BALE.id) {
            return fallDistance * 0.5F;
        } else {
            return fallDistance;
        }
    }
}
