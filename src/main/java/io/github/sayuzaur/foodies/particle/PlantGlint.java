/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
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
public class PlantGlint extends Particle implements ParticleDisableQuadDraw {
    private static final String TEXTURE_PATH = "/assets/foodies/stationapi/textures/particle/";
    private final int texIndex;
    private float alpha;

    protected String getTextureName() {
        return "";
    }

    protected int getTextureCount() {
        return 1;
    }

    public PlantGlint(World world, int x, int y, int z) {
        super(world, x, y, z, 0, 0, 0);
        this.setPosition(x + random.nextFloat(), y + 0.1F + (random.nextFloat() * 0.5F), z + random.nextFloat());
        this.alpha = 0.7F + (random.nextFloat() * 0.2F);
        this.scale = 0.8F + (random.nextFloat() * 0.1F);
        this.texIndex = random.nextInt(getTextureCount());

        this.gravityStrength = 0.0008F;
        this.velocityY = 0.02F + (this.random.nextFloat() * 0.01F);
        this.noClip = false;

        this.maxParticleAge = random.nextInt(10) + 25;
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
        float light = this.getBrightnessAtEyes(1.0F);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.INSTANCE.textureManager.getTextureId(TEXTURE_PATH + getTextureName() + texIndex + ".png"));
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);

        tess.startQuads();
        tess.color(red * light, green * light, blue * light, alpha);
        tess.vertex(partialPosX - horizontalSize * scalePar - widthOffset * scalePar, partialPosY - verticalSize * scalePar, partialPosZ - depthSize * scalePar - heightOffset * scalePar, 1, 1);
        tess.vertex(partialPosX - horizontalSize * scalePar + widthOffset * scalePar, partialPosY + verticalSize * scalePar, partialPosZ - depthSize * scalePar + heightOffset * scalePar, 1, 0);
        tess.vertex(partialPosX + horizontalSize * scalePar + widthOffset * scalePar, partialPosY + verticalSize * scalePar, partialPosZ + depthSize * scalePar + heightOffset * scalePar, 0, 0);
        tess.vertex(partialPosX + horizontalSize * scalePar - widthOffset * scalePar, partialPosY - verticalSize * scalePar, partialPosZ + depthSize * scalePar - heightOffset * scalePar, 0, 1);
        tess.draw();

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @Override
    public int getGroup() {
        return 3;
    }

    @Override
    public void tick() {
        this.prevY = this.y;

        ++particleAge;

        if (this.particleAge >= this.maxParticleAge || this.scale <= 0.0F) {
            this.markDead();
        }

        this.velocityY = this.velocityY - this.gravityStrength;
        if (this.velocityY < 0.0F) {
            this.velocityY = 0.0F;
            ++particleAge;
        }
        this.move(0.0F, this.velocityY, 0.0F);

        if (this.particleAge >= this.maxParticleAge - 10) {
            if (this.scale >= 0.0F) {
                this.alpha = this.alpha - 0.1F;
            }
        }
    }
}