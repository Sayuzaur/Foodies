package io.github.sayuzaur.foodies.block.plant;

import io.github.sayuzaur.foodies.events.init.BlockListener;
import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.util.Identifier;

public class CabbageWild extends BaseWildCrop {
    public CabbageWild(Identifier identifier) {
        super(identifier);
    }

    @Override
    protected Item getDrop() {
        return ItemListener.CABBAGE_SEEDS;
    }

    @Override
    protected Block getShearsDrop() {
        return BlockListener.CABBAGE_WILD;
    }
}