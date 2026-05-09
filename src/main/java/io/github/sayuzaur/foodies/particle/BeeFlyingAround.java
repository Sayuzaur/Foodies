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

package io.github.sayuzaur.foodies.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class BeeFlyingAround extends BeeBase {
    public BeeFlyingAround(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
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

        if (particleAge % 20 == 18) {
            this.velocityX *= -1;
            this.velocityZ *= -1;
        }

        if (this.particleAge >= this.maxParticleAge - 5) {
            if (this.scale >= 0.0F) {
                this.scale = this.scale - 0.1F;
            }
        }

        if (this.particleAge >= this.maxParticleAge) {
            this.markDead();
        } else {
            if (random.nextInt(8) == 0) {
                this.velocityY = velocityY + (this.random.nextFloat() + this.random.nextFloat() - 1) / 20.0F;
                this.velocityY *= -1;
            }
            if (random.nextInt(8) == 0) {
                this.velocityX = velocityX + (this.random.nextFloat() + this.random.nextFloat() - 1) / 40.0F;
            }
            if (random.nextInt(8) == 0) {
                this.velocityZ = velocityZ + (this.random.nextFloat() + this.random.nextFloat() - 1) / 40.0F;
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
