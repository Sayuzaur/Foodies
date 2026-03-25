package io.github.sayuzaur.foodies.block.plant;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.util.Identifier;

public class TomatoWild extends BaseWildCrop {
    public TomatoWild(Identifier identifier) {
        super(identifier);
    }

    @Override
    protected Item getDrop() {
        return ItemListener.TOMATO_SEEDS;
    }

    @Override
    protected Block getShearsDrop() {
        return BlockListener.TOMATO_WILD;
    }
}
