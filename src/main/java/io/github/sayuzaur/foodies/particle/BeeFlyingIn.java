/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class BeeFlyingIn extends BeeBase {
    public BeeFlyingIn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        float randX = this.random.nextFloat() + this.random.nextFloat() - 1;
        float randZ = this.random.nextFloat() + this.random.nextFloat() - 1;
        this.setPosition(x + 0.5 + (randX * 2), y, z + 0.5 + (randZ * 2));
        this.scale = (random.nextFloat() * 0.5F);
        this.velocityY = (this.random.nextFloat() + this.random.nextFloat() - 1) / 200.0F;
        this.velocityX = Math.round((this.velocityX * 0.6F) * -10);
        this.velocityZ = Math.round((this.velocityZ * 0.6F) * -10);

        if (this.velocityX == 0 && this.velocityZ == 0) {
            this.maxParticleAge = 0;
        } else {
            this.velocityX = (this.velocityX / 10.0F + 0.001F) + (randX * -0.065F);
            this.velocityZ = (this.velocityZ / 10.0F + 0.001F) + (randZ * -0.065F);

            this.maxParticleAge = random.nextInt(10) + 50;
        }
    }

    @Override
    public void tick() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;

        ++particleAge;

        if (this.particleAge <= 5) {
            this.scale = this.scale + 0.2F;
        }

        if (this.particleAge >= this.maxParticleAge - 5) {
            if (this.scale >= 0.0F) {
                this.scale = this.scale - 0.1F;
            }
        }

        if (this.particleAge >= this.maxParticleAge) {
            this.markDead();
        } else {
            this.move(this.velocityX, this.velocityY, this.velocityZ);

            if (this.y == this.prevY) {
                this.velocityY *= -1;
            }
            if (this.x == this.prevX) {
                this.markDead();
            }
            if (this.z == this.prevZ) {
                this.markDead();
            }
        }
    }
}