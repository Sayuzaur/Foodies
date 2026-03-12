package io.github.sayuzaur.foodies.item.crops;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Chili extends TemplateStackableFoodItem{
    public Chili(Identifier identifier) {
        super(identifier, 1, false, 64);
    }

    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        super.use(stack, world, user);
        user.fireTicks = 100;
        return stack;
    }
}
