package io.github.sayuzaur.foodies.item.food;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BaseStew extends TemplateFoodItem {
    public BaseStew(Identifier identifier, int healAmount, boolean isWolfFood) {
        super(identifier, healAmount, isWolfFood);
    }
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        super.use(stack, world, user);
        return new ItemStack(Item.BOWL);
    }
}
