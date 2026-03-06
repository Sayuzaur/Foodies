package io.github.sayuzaur.foodies.item;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Bottle extends TemplateItem {
    public Bottle(Identifier identifier){
        super(identifier);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        int blockId = world.getBlockId(x, y, z);
        if (blockId == Block.CACTUS.id) {
            if (!world.isRemote) {
                ItemStack bottleStack = new ItemStack(ItemListener.JUICE_CACTUS);
                ItemEntity bottleItemEntity = new ItemEntity(world, user.x, user.y - 1.7F, user.z, bottleStack);
                world.spawnEntity(bottleItemEntity);

                --stack.count;
            }
            return true;
        }
        return false;
    }
}
