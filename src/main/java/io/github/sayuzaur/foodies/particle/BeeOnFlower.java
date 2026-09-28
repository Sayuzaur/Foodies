/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class BeeOnFlower extends BeeBase {
    public BeeOnFlower(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.setPosition(x + random.nextFloat(), y + 0.4F + (random.nextFloat() * 0.5F), z + random.nextFloat());
        this.scale = (random.nextFloat() * 0.5F);
        this.velocityY = this.random.nextFloat() / 80.0F + 0.005F;
        this.velocityX = 0.0F;
        this.velocityZ = 0.0F;
        this.maxParticleAge = random.nextInt(80) + 60;
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
            if (random.nextInt(3) == 0) {
                this.velocityY *= -1;
            }

            this.move(this.velocityX, this.velocityY, this.velocityZ);

            if (this.y == this.prevY) {
                this.velocityY = this.velocityY + 0.02F;
            }
        }
    }
}