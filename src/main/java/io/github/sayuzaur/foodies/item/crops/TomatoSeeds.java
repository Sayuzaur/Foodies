package io.github.sayuzaur.foodies.item.crops;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class TomatoSeeds extends TemplateItem {
    public TomatoSeeds(Identifier identifier){
        super(identifier);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {

        if (world.getBlockId(x, y, z) == Block.FARMLAND.id && side == 1) {
            world.setBlock(x, y + 1, z, BlockListener.TOMATO_CROPS.id);
            stack.count--;
            return true;
        }
        return false;
    }
}
