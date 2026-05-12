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

package io.github.sayuzaur.foodies.block.crops;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

//FIXME Get rid of all this shit, extend RegrowingCrops

public class ChiliCrops extends TemplateBlock implements BlockTemplate {

    public static final IntProperty AGE10;
    static {
        AGE10 = IntProperty.of("age", 0,10);
    }

    public ChiliCrops(Identifier identifier){
        super(identifier, Material.PLANT);
        this.setTickRandomly(true);
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
        this.setSoundGroup(DIRT_SOUND_GROUP);
        setDefaultState(getStateManager().getDefaultState().with(AGE10, 0));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE10);
    }

    int seedCount = 1;
    int cropCount = 2;
    int cropChance = 9;

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(AGE10, 0);
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    protected boolean canPlantOnTop(int id) {
        return id == Block.GRAVEL.id;
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return super.canPlaceAt(world, x, y, z)
                && this.canPlantOnTop(world.getBlockId(x, y - 1, z));
    }

    @Override
    public boolean canGrow(World world, int x, int y, int z) {
        return (world.getBrightness(x, y, z) >= 2 || world.hasSkyLight(x, y, z)) && this.canPlantOnTop(world.getBlockId(x, y - 1, z));
    }

    protected final void breakIfCannotGrow(World world, int x, int y, int z) {
        if (!this.canGrow(world, x, y, z)) {
            this.dropStacks(world, x, y, z, world.getBlockState(x, y, z));
            world.setBlock(x, y, z, 0);
        }
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);
        this.breakIfCannotGrow(world, x, y, z);
    }

    public void applyFullGrowth(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);
        world.setBlockStateWithNotify(x, y, z, state.with(AGE10, 10));
    }

    private boolean isLavaNearby(World world, int x, int y, int z) {
        for(int var1 = x - 4; var1 <= x + 4; ++var1) {
            for(int var2 = y; var2 <= y + 1; ++var2) {
                for(int var3 = z - 4; var3 <= z + 4; ++var3) {
                    if (world.getMaterial(var1, var2, var3) == Material.LAVA) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private float getAvailableMoisture(World world, int x, int y, int z) {
        float moisture = 1.0F;
        int var6 = world.getBlockId(x, y, z - 1);
        int var7 = world.getBlockId(x, y, z + 1);
        int var8 = world.getBlockId(x - 1, y, z);
        int var9 = world.getBlockId(x + 1, y, z);
        int var10 = world.getBlockId(x - 1, y, z - 1);
        int var11 = world.getBlockId(x + 1, y, z - 1);
        int var12 = world.getBlockId(x + 1, y, z + 1);
        int var13 = world.getBlockId(x - 1, y, z + 1);
        boolean var14 = var8 == this.id || var9 == this.id;
        boolean var15 = var6 == this.id || var7 == this.id;
        boolean var16 = var10 == this.id || var11 == this.id || var12 == this.id || var13 == this.id;

        for(int var17 = x - 1; var17 <= x + 1; ++var17) {
            for(int var18 = z - 1; var18 <= z + 1; ++var18) {
                int var19 = world.getBlockId(var17, y - 1, var18);
                float var20 = 0.0F;
                if (var19 == Block.GRAVEL.id) {
                    var20 = 1.0F;
                    if (isLavaNearby(world, var17, y - 1, var18)) {
                        var20 = 3.0F;
                    }
                }

                if (var17 != x || var18 != z) {
                    var20 /= 4.0F;
                }

                moisture += var20;
            }
        }

        if (var16 || var14 && var15) {
            moisture /= 2.0F;
        }
        if (!isLavaNearby(world, x, y - 1, z)) {
            moisture = 0.0F;
        }
        return moisture;
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        this.breakIfCannotGrow(world, x, y, z);

        if (world.getLightLevel(x, y + 1, z) >= 2) {
            BlockState state = world.getBlockState(x, y, z);
            int age = state.get(AGE10);

            if (age < 10) {
                float moisture = this.getAvailableMoisture(world, x, y, z);
                if (moisture != 0.0F) {
                    if (random.nextInt((int)(100.0F / moisture)) == 0) {
                        ++age;
                        world.setBlockStateWithNotify(x, y, z, state.with(AGE10, age));
                    }
                }
            }
        }
    }
    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck) {
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, BlockState state, int meta) {
        dropStacks(world, x, y, z, state);

        super.afterBreak(world, player, x, y, z, state, meta);
    }

    public void dropStacks(World world, int x, int y, int z, BlockState state) {
        if (!world.isRemote) {
            int age = state.get(AGE10);

            ItemStack baseStack = new ItemStack(ItemListener.CHILI_SEEDS, seedCount);
            ItemEntity baseCropsItemEntity = new ItemEntity(world, x + 0.5f, y + 1.0f, z + 0.5f, baseStack);
            baseCropsItemEntity.pickupDelay = 10;
            world.spawnEntity(baseCropsItemEntity);

            if (age >= 10){
                for(int i = 0; i < cropCount; ++i) {
                    if (world.random.nextInt(10) <= cropChance) {

                        float varBase = 0.7F;
                        float varX = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                        float varY = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                        float varZ = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;

                        ItemStack stack = new ItemStack(ItemListener.CHILI);
                        ItemEntity cropsItemEntity = new ItemEntity(world,((float)x + varX),((float)y + varY),((float)z + varZ), stack);
                        cropsItemEntity.pickupDelay = 10;
                        world.spawnEntity(cropsItemEntity);
                    }
                }
            }
        }
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (!world.isRemote) {
            BlockState state = world.getBlockState(x, y, z);
            int age = state.get(AGE10);

            if (age == 10) {
                age = 7;
                world.setBlockStateWithNotify(x, y, z, state.with(AGE10, age));

                world.playSound(x, y, z, "mob.chickenplop", 0.5F, 0.4F);

                for (int i = 0; i < cropCount; ++i) {

                    float varBase = 0.7F;
                    float varX = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                    float varY = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                    float varZ = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;

                    ItemStack stack = new ItemStack(ItemListener.CHILI);
                    ItemEntity cropsItemEntity = new ItemEntity(world,((float)x + varX),((float)y + varY),((float)z + varZ), stack);
                    cropsItemEntity.pickupDelay = 10;
                    world.spawnEntity(cropsItemEntity);
                }

                return true;

            } else if (age < 10) {
                ItemStack stack = player.getHand();
                if (stack != null && player.getHand().itemId == Item.GLOWSTONE_DUST.id) {
                    this.applyFullGrowth(world, x, y, z);
                    --stack.count;

                    return true;

                }
            }
        }
        return true;
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        BlockState state = world.getBlockState(x, y, z);
        int age = state.get(AGE10);

        if (age == 10 && random.nextInt(10) == 0) {
            double var31 = (double)((float)x + random.nextFloat());
            double var32 = (double)y + this.maxY;
            double var33 = (double)((float)z + random.nextFloat());
            world.addParticle("smoke", var31, var32, var33, (double)0.0F, (double)0.0F, (double)0.0F);
        }
    }

}
