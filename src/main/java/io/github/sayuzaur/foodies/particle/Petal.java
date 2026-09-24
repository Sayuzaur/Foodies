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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class Petal extends Particle implements ParticleDisableQuadDraw {
    public static final String TEXTURE_PATH = "/assets/foodies/stationapi/textures/particle/petal_";
    private final String textureName;
    private float alpha;
    private float rotation = 0.0F;
    private float rotationSpeed;

    public Petal(World world, int x, int y, int z, double fVelocityX, double fVelocityY, double fVelocityZ, String petalName) {
        super(world, x, y, z, 0, 0, 0);
        this.setPosition(x + random.nextFloat(), y - 0.1F, z + random.nextFloat());
        this.alpha = 0.7F + (random.nextFloat() * 0.2F);
        this.scale = 0.8F + (random.nextFloat() * 0.1F);
        this.gravityStrength = 0.0005F;
        this.velocityX = fVelocityX + (this.random.nextFloat() * 0.01F);
        this.velocityY = fVelocityY + (this.random.nextFloat() * 0.01F);
        this.velocityZ = fVelocityZ + (this.random.nextFloat() * 0.01F);
        this.noClip = false;

        this.maxParticleAge = random.nextInt(50) + 150;
        this.rotationSpeed = (random.nextFloat() * 0.15F) - (random.nextFloat() * 0.15F);

        this.textureName = petalName;
    }

    @Override
    public void render(Tessellator tess, float partialTicks, float horizontalSize, float verticalSize, float depthSize, float widthOffset, float heightOffset) {
        LivingEntity view = Minecraft.INSTANCE.camera;
        double interpX = view.lastTickX + (view.x - view.lastTickX) * partialTicks;
        double interpY = view.lastTickY + (view.y - view.lastTickY) * partialTicks;
        double interpZ = view.lastTickZ + (view.z - view.lastTickZ) * partialTicks;
        float partialPosX = (float) (prevX + (x - prevX) * partialTicks - interpX);
        float partialPosY = (float) (prevY + (y - prevY) * partialTicks - interpY);
        float partialPosZ = (float) (prevZ + (z - prevZ) * partialTicks - interpZ);
        float scalePar = 0.1F * scale;
        float brightness = this.getBrightnessAtEyes(1.0F);

        float angle = rotation + rotationSpeed * partialTicks;
        float sin = MathHelper.sin(angle);
        float cos = MathHelper.cos(angle);

        float u1 = 0.5F + (-0.5F * cos - -0.5F * sin);
        float v1 = 0.5F + (-0.5F * sin + -0.5F * cos);
        float u2 = 0.5F + ( 0.5F * cos - -0.5F * sin);
        float v2 = 0.5F + ( 0.5F * sin + -0.5F * cos);
        float u3 = 0.5F + ( 0.5F * cos -  0.5F * sin);
        float v3 = 0.5F + ( 0.5F * sin +  0.5F * cos);
        float u4 = 0.5F + (-0.5F * cos -  0.5F * sin);
        float v4 = 0.5F + (-0.5F * sin +  0.5F * cos);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.INSTANCE.textureManager.getTextureId(TEXTURE_PATH + this.textureName + ".png"));
        //GL11 things stolen from renderShadow method in EntityRenderer, I don't understand it fully, but it works for transparency.
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);

        tess.startQuads();
        tess.color(red * brightness, green * brightness, blue * brightness, alpha);
        tess.vertex(partialPosX - horizontalSize * scalePar - widthOffset * scalePar, partialPosY - verticalSize * scalePar, partialPosZ - depthSize * scalePar - heightOffset * scalePar, u1, v1);
        tess.vertex(partialPosX - horizontalSize * scalePar + widthOffset * scalePar, partialPosY + verticalSize * scalePar, partialPosZ - depthSize * scalePar + heightOffset * scalePar, u2, v2);
        tess.vertex(partialPosX + horizontalSize * scalePar + widthOffset * scalePar, partialPosY + verticalSize * scalePar, partialPosZ + depthSize * scalePar + heightOffset * scalePar, u3, v3);
        tess.vertex(partialPosX + horizontalSize * scalePar - widthOffset * scalePar, partialPosY - verticalSize * scalePar, partialPosZ + depthSize * scalePar - heightOffset * scalePar, u4, v4);
        tess.draw();

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @Override
    public void tick() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;

        ++particleAge;

        if (this.particleAge >= this.maxParticleAge || this.scale <= 0.0F) {
            this.markDead();
        }

        this.velocityY = this.velocityY - this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);

        if (this.onGround) {
            this.velocityX = 0;
            this.velocityY = 0;
            this.velocityZ = 0;
            this.y = prevY - 0.0001F;

            rotationSpeed = 0;
        }

        rotation += rotationSpeed;

        if (this.particleAge >= this.maxParticleAge - 20) {
            if (this.scale >= 0.0F) {
                this.scale = this.scale - 0.03F;
                this.alpha = this.alpha - 0.05F;
                if (this.onGround) {
                    this.y = prevY - 0.005F;
                }
            }
        }
    }
}
