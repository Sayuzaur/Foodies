/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class BeeFlyingOut extends BeeBase {
    public BeeFlyingOut(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.setPosition(x + 0.5, y, z + 0.5);
        this.scale = 1.0F + (random.nextFloat() * 0.5F);
        this.velocityY = (this.random.nextFloat() + this.random.nextFloat() - 1) / 80.0F;
        this.velocityX = this.velocityX * 0.5F;
        this.velocityZ = this.velocityZ * 0.5F;
        this.maxParticleAge = random.nextInt(40) + 80;
    }

    @Override
    public void tick() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;

        ++particleAge;

        if (this.particleAge >= this.maxParticleAge - 5) {
            if (this.scale >= 0.0F) {
                this.scale = this.scale - 0.1F;
            }
        }

        if (this.particleAge >= this.maxParticleAge) {
            this.markDead();
        } else {
            if (random.nextInt(8) == 0) {
                this.velocityY = velocityY + (this.random.nextFloat() + this.random.nextFloat() - 1) / 40.0F;
                this.velocityY *= -1;
            }
            if (random.nextInt(8) == 0) {
                this.velocityX = velocityX + (this.random.nextFloat() + this.random.nextFloat() - 1) / 20.0F;
            }
            if (random.nextInt(8) == 0) {
                this.velocityZ = velocityZ + (this.random.nextFloat() + this.random.nextFloat() - 1) / 20.0F;
            }
            this.move(this.velocityX, this.velocityY, this.velocityZ);

            if (this.y == this.prevY) {
                this.velocityY *= -1;
            }
            if (this.x == this.prevX) {
                this.velocityX *= -1;
            }
            if (this.z == this.prevZ) {
                this.velocityZ *= -1;
            }
        }
    }
}
