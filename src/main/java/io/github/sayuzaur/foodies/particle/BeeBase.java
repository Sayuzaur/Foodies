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
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Tessellator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/*
 * Most of the code below is reused from github.com/FarnGitHub/Campfire-StationAPI/
 * made by Farn
 */

public class BeeBase extends Particle implements ParticleDisableQuadDraw {
    public static final int TEXTURE_COUNT = 6;
    public static final String[] TEXTURES = new String[TEXTURE_COUNT];
    static
    {
        for (int i = 0; i < TEXTURE_COUNT; i++)
            TEXTURES[i] = "/assets/foodies/stationapi/textures/particle/bee_" + i + ".png";
    }
    protected final int texIndex;

    public BeeBase(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        this.texIndex = random.nextInt(TEXTURE_COUNT);
        this.noClip = false;
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
}
