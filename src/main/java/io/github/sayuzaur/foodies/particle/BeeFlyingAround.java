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

import farn.farn_util.api.particle.ParticleDisableQuadDraw;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Tessellator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class BeeFlyingAround extends Particle implements ParticleDisableQuadDraw {
    public static final int TEXTURE_COUNT = 6;
    public static final String[] TEXTURES = new String[TEXTURE_COUNT];
    static
    {
        for (int i = 0; i < TEXTURE_COUNT; i++)
            TEXTURES[i] = "/assets/foodies/stationapi/textures/particle/bee_" + i + ".png";
    }

    protected final int texIndex;

    public BeeFlyingAround(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.setPosition(x + 0.5,
                y,
                z + 0.5);
        this.scale = 1.0F + (random.nextFloat() * 0.5F);
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        this.velocityY = (this.random.nextFloat() + this.random.nextFloat() - 1) / 80.0F;
        this.velocityX = this.velocityX * 0.5F;
        this.velocityZ = this.velocityZ * 0.5F;

        this.noClip = false;
        this.maxParticleAge = random.nextInt(40) + 80;
        this.texIndex = random.nextInt(TEXTURE_COUNT);
    }

    @Override
    public void render(Tessellator tess, float partialTicks, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY) {
        LivingEntity view = Minecraft.INSTANCE.camera;
        double interpX = view.lastTickX + (view.x - view.lastTickX) * partialTicks;
        double interpY = view.lastTickY + (view.y - view.lastTickY) * partialTicks;
        double interpZ = view.lastTickZ + (view.z - view.lastTickZ) * partialTicks;
        float partialPosX = (float) (prevX + (x - prevX) * partialTicks - interpX);
        float partialPosY = (float) (prevY + (y - prevY) * partialTicks - interpY);
        float partialPosZ = (float) (prevZ + (z - prevZ) * partialTicks - interpZ);
        float scalePar = 0.1F * scale;
        float light = this.getBrightnessAtEyes(1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.INSTANCE.textureManager.getTextureId(TEXTURES[this.texIndex % TEXTURES.length]));
        tess.startQuads();
        tess.color(red * light, green * light, blue * light);
        tess.vertex(partialPosX - rotX * scalePar - rotYZ * scalePar, partialPosY - rotXZ * scalePar, partialPosZ - rotZ * scalePar - rotXY * scalePar, 1, 1);
        tess.vertex(partialPosX - rotX * scalePar + rotYZ * scalePar, partialPosY + rotXZ * scalePar, partialPosZ - rotZ * scalePar + rotXY * scalePar, 1, 0);
        tess.vertex(partialPosX + rotX * scalePar + rotYZ * scalePar, partialPosY + rotXZ * scalePar, partialPosZ + rotZ * scalePar + rotXY * scalePar, 0, 0);
        tess.vertex(partialPosX + rotX * scalePar - rotYZ * scalePar, partialPosY - rotXZ * scalePar, partialPosZ + rotZ * scalePar - rotXY * scalePar, 0, 1);
        tess.draw();
    }

    @Override
    public int getGroup()
    {
        return 3;
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
