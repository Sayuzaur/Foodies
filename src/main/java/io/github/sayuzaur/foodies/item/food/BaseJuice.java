package io.github.sayuzaur.foodies.item.food;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BaseJuice extends TemplateStackableFoodItem {
    public BaseJuice(Identifier identifier, int healAmount) {
        super(identifier, healAmount, false, 3);
    }

    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        super.use(stack, world, user);

        ItemStack bottleStack = new ItemStack(ItemListener.BOTTLE);
        ItemEntity bottleItemEntity = new ItemEntity(world, user.x, user.y - 1.7F, user.z, bottleStack);
        world.spawnEntity(bottleItemEntity);
        //user.dropItem(bottleStack);

        return stack;
    }
}
