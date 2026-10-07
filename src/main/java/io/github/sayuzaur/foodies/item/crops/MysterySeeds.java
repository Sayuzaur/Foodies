/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.item.crops;

import io.github.sayuzaur.foodies.block.PlantLogic;
import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.api.util.Identifier;

public class MysterySeeds extends TemplateItem implements CustomTooltipProvider {
    private static final Block DUMMY_CROP_BLOCK = BlockListener.CARROT_CROPS;

    public MysterySeeds(Identifier identifier){
        super(identifier);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {
        if (DUMMY_CROP_BLOCK.canPlaceAt(world, x, y + 1, z) && side == 1) {
            int randCrop = random.nextInt(5);

            switch (randCrop) {
                case 0 -> world.setBlock(x, y + 1, z, BlockListener.CARROT_CROPS.id);
                case 1 -> world.setBlock(x, y + 1, z, BlockListener.POTATO_CROPS.id);
                case 2 -> world.setBlock(x, y + 1, z, BlockListener.ONION_CROPS.id);
                case 3 -> world.setBlock(x, y + 1, z, BlockListener.TOMATO_CROPS.id);
                case 4 -> world.setBlock(x, y + 1, z, BlockListener.CABBAGE_CROPS.id);
            }
            stack.count--;

            PlantLogic.plantingClientEffect(world, x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        return new String[]{originalTooltip,
                Formatting.GRAY + "Can grow into any overworld crop"};
    }
}
