/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.context.BlockTagContext;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.registry.tag.BlockTags;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Optional;
import java.util.Random;

import static io.github.sayuzaur.foodies.FoodiesConfig.CROPS_CONFIG;

public class PlantLogic {
    public static final int BASE_GROW_CHANCE = 100;
    public static final float BASE_GROW_CHANCE_MODIFIER = 0.1F;
    public static final float BEENEST_GROW_CHANCE_MODIFIER = 1.8F;

    //VERY BASIC PRESETS

    public static boolean plantOnDirt(World world, int x, int y, int z) {
        return     world.getBlockState(x, y, z).isIn(BlockTags.GRASS_BLOCKS, BlockTagContext.of(world, x, y, z))
                || world.getBlockState(x, y, z).isIn(BlockTags.DIRTS, BlockTagContext.of(world, x, y, z));
    }

    public static boolean plantOnFarmland(World world, int x, int y, int z) {
        return     world.getBlockState(x, y, z).isIn(BlockTags.FARMLANDS, BlockTagContext.of(world, x, y, z));
    }

    public static boolean plantOnGravel(World world, int x, int y, int z) {
        return     world.getBlockState(x, y, z).isIn(BlockTags.GRAVELS, BlockTagContext.of(world, x, y, z));
    }

    public static int defaultLightLevel() {
        return CROPS_CONFIG.lightLevelRequired;
    }

    //SOME BASIC LOGIC

    public static float getVanillaMoisture(World world, int x, int y, int z) {
        //It's vanilla moisture method used by wheat crops. Only change is using blockTag for farmland instead of hardcodded ID.
        int cropBlockId = world.getBlockId(x, y, z);
        float moisture = 1.0F;
        float addMoisture = 0.0F;
        int sideZ1 = world.getBlockId(x, y, z - 1);
        int sideZ2 = world.getBlockId(x, y, z + 1);
        int sideX1 = world.getBlockId(x - 1, y, z);
        int sideX2 = world.getBlockId(x + 1, y, z);
        int sideXZ1 = world.getBlockId(x - 1, y, z - 1);
        int sideXZ2 = world.getBlockId(x + 1, y, z - 1);
        int sideXZ3 = world.getBlockId(x + 1, y, z + 1);
        int sideXZ4 = world.getBlockId(x - 1, y, z + 1);
        boolean checkSidesX = sideX1 == cropBlockId || sideX2 == cropBlockId;
        boolean checkSidesZ = sideZ1 == cropBlockId || sideZ2 == cropBlockId;
        boolean checkSidesXZ = sideXZ1 == cropBlockId|| sideXZ2 == cropBlockId|| sideXZ3 == cropBlockId || sideXZ4 == cropBlockId;

        for(int checkX = x - 1; checkX <= x + 1; ++checkX) {
            for(int checkZ = z - 1; checkZ <= z + 1; ++checkZ) {
                if (world.getBlockState(checkX, y - 1, checkZ).isIn(BlockTags.FARMLANDS, BlockTagContext.of(world, checkX, y - 1, checkZ))) {
                    addMoisture = 1.0F;
                    if (world.getBlockMeta(checkX, y - 1, checkZ) > 0) {
                        //It assumes custom farmland blocks also uses meta instead of blockstates for moisture. Idc now to expand it.
                        addMoisture = 3.0F;
                    }
                }

                if (checkX != x || checkZ != z) {
                    addMoisture /= 4.0F;
                }

                moisture += addMoisture;
            }
        }

        if (checkSidesXZ || checkSidesX && checkSidesZ) {
            moisture /= 2.0F;
        }

        return moisture;
    }

    public static float getHellishMoisture(World world, int x, int y, int z) {
        //Vanilla method but uses gravel instead of farmlands and lava to replace water.
        int cropBlockId = world.getBlockId(x, y, z);
        float moisture = 1.0F;
        float addMoisture = 0.0F;
        int sideZ1 = world.getBlockId(x, y, z - 1);
        int sideZ2 = world.getBlockId(x, y, z + 1);
        int sideX1 = world.getBlockId(x - 1, y, z);
        int sideX2 = world.getBlockId(x + 1, y, z);
        int sideXZ1 = world.getBlockId(x - 1, y, z - 1);
        int sideXZ2 = world.getBlockId(x + 1, y, z - 1);
        int sideXZ3 = world.getBlockId(x + 1, y, z + 1);
        int sideXZ4 = world.getBlockId(x - 1, y, z + 1);
        boolean checkSidesX = sideX1 == cropBlockId || sideX2 == cropBlockId;
        boolean checkSidesZ = sideZ1 == cropBlockId || sideZ2 == cropBlockId;
        boolean checkSidesXZ = sideXZ1 == cropBlockId|| sideXZ2 == cropBlockId|| sideXZ3 == cropBlockId || sideXZ4 == cropBlockId;

        for(int checkX = x - 1; checkX <= x + 1; ++checkX) {
            for(int checkZ = z - 1; checkZ <= z + 1; ++checkZ) {
                if (world.getBlockState(checkX, y - 1, checkZ).isIn(BlockTags.GRAVELS, BlockTagContext.of(world, checkX, y - 1, checkZ))) {
                    addMoisture = 1.0F;
                    if (isLavaNearby(world, checkX, y - 1, checkZ)) {
                        addMoisture = 3.0F;
                    }
                }
                if (checkX != x || checkZ != z) {
                    addMoisture /= 4.0F;
                }

                moisture += addMoisture;
            }
        }

        if (checkSidesXZ || checkSidesX && checkSidesZ) {
            moisture /= 2.0F;
        }

        if (!isLavaNearby(world, x, y - 1, z)) {
            moisture = 0.0F;
        }

        return moisture;
    }

    private static boolean isLavaNearby(World world, int x, int y, int z) {
        int searchRange = 4;
        for(int lavaX = x - searchRange; lavaX <= x + searchRange; ++lavaX) {
            for(int lavaY = y - 1; lavaY <= y + 1; ++lavaY) {
                for(int lavaZ = z - searchRange; lavaZ <= z + searchRange; ++lavaZ) {
                    if (world.getMaterial(lavaX, lavaY, lavaZ) == Material.LAVA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isBeenestNearby(World world, int x, int y, int z) {
        int searchRange = 4;
        for(int beenestX = x - searchRange; beenestX <= x + searchRange; ++beenestX) {
            for(int beenestY = y - searchRange; beenestY <= y + searchRange; ++beenestY) {
                for(int beenestZ = z - searchRange; beenestZ <= z + searchRange; ++beenestZ) {
                    //TODO Introduce beenest tag, check by tag
                    if (world.getBlockId(beenestX, beenestY, beenestZ) == BlockListener.BEEHIVE_OAK.id) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean growAttemptVanilla(World world, int x, int y, int z, Random random) {
        float growChanceModifier = BASE_GROW_CHANCE_MODIFIER;
        //Add moisture
        growChanceModifier += getVanillaMoisture(world, x, y, z);
        //Add bee modifier
        if (isBeenestNearby(world, x, y, z)) {
            growChanceModifier += BEENEST_GROW_CHANCE_MODIFIER;
        }
        //Grow check
        return random.nextInt((int) (PlantLogic.BASE_GROW_CHANCE / growChanceModifier)) == 0;
    }

    public static boolean growAttemptHellish(World world, int x, int y, int z, Random random) {
        float growChanceModifier = BASE_GROW_CHANCE_MODIFIER;
        //Add moisture
        growChanceModifier += getHellishMoisture(world, x, y, z);
        //Add bee modifier
        if (isBeenestNearby(world, x, y, z)) {
            growChanceModifier += BEENEST_GROW_CHANCE_MODIFIER;
        }
        //Grow check
        return random.nextInt((int) (PlantLogic.BASE_GROW_CHANCE / growChanceModifier)) == 0;
    }

    //System.out.println(growChanceModifier);

    public static int identifierToItemId(String n) {
        Optional<Item> item = ItemRegistry.INSTANCE.getOrEmpty(Identifier.of(n));
        return item.map(itemBase -> itemBase.id).orElse(-1);
    }

    public static int getGrowthBlockItemId() {
        return identifierToItemId(CROPS_CONFIG.blockGrowthItem);
    }

    //CLIENTSIDE EFFECTS

    public static void bonemealClientEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, "step.grass", 1.0F, 1.6F);
    }

    public static void harvestClientEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, "mob.chickenplop", 0.5F, 0.4F);
    }

    public static void plantingClientEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, "step.grass", 1.0F, 1.0F);
    }

    public static void blockGrowthClientEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, "mob.cow", 0.5F, 0.4F);
    }

    public static void snowloggingClientEffect(World world, int x, int y, int z) {
        world.playSound(x, y, z, Block.SNOW.soundGroup.getSound(), 0.5F, 0.4F);
    }
}
