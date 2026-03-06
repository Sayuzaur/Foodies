package io.github.sayuzaur.foodies.item.crops;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Carrot extends TemplateStackableFoodItem {
    public Carrot(Identifier identifier){
        super(identifier, 1, false, 64);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity player, World world, int x, int y, int z, int side) {

        if (world.getBlockId(x, y, z) == Block.FARMLAND.id && side == 1) {
            world.setBlock(x, y + 1, z, BlockListener.CARROT_CROPS.id);
            stack.count--;
            return true;
        }
        return false;
    }
}
