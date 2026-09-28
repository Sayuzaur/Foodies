/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block.plant;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.context.BlockTagContext;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.registry.tag.BlockTags;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Optional;
import java.util.Random;

import static io.github.sayuzaur.foodies.FoodiesMod.CROPS_CONFIG;

public class BasePlant extends TemplateBlock {
    public BasePlant(Identifier identifier) {
        super(identifier, Material.PLANT);
        this.setSoundGroup(DIRT_SOUND_GROUP);
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

    public boolean canPlantOnTop(World world, int x, int y, int z) {
        return false;
    }

    public boolean plantOnDirts(World world, int x, int y, int z) {
        return     world.getBlockState(x, y, z).isIn(BlockTags.GRASS_BLOCKS, BlockTagContext.of(world, x, y, z))
                || world.getBlockState(x, y, z).isIn(BlockTags.DIRTS, BlockTagContext.of(world, x, y, z));
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z, int side) {
        if (!world.isAir(x, y, z)) {
            return false;
        }
        return canPlantOnTop(world, x, y - 1, z);
    }

    @Override
    public boolean canGrow(World world, int x, int y, int z) {
        return canPlantOnTop(world, x, y - 1, z);
    }

    protected final void breakIfCannotGrow(World world, int x, int y, int z) {
        if (!this.canGrow(world, x, y, z)) {
            this.dropStacks(world, x, y, z, world.getBlockMeta(x, y, z));
            world.setBlock(x, y, z, 0);
        }
    }

    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);
        this.breakIfCannotGrow(world, x, y, z);
    }

    public void onTick(World world, int x, int y, int z, Random random) {
        this.breakIfCannotGrow(world, x, y, z);
    }

    //TODO Move it to some Util class in future.
    // Now it's used only there so it doesn't matter, but would be better in the future.
    public static int identifierToItemId(String n) {
        Optional<Item> item = ItemRegistry.INSTANCE.getOrEmpty(Identifier.of(n));
        return item.map(itemBase -> itemBase.id).orElse(-1);
    }

    public int getGrowthBlockItemId() {
        return identifierToItemId(CROPS_CONFIG.blockGrowthItem);
    }

    public void bonemealClientsideEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, "step.grass", 1.0F, 1.6F);
    }

    public void harvestClientsideEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, "mob.chickenplop", 0.5F, 0.4F);
    }

    public void blockGrowthClientsideEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, "mob.cow", 0.5F, 0.4F);
    }

    public void snowloggingClientsideEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, Block.SNOW.soundGroup.getSound(), 0.5F, 0.4F);
    }
}
