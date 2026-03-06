package io.github.sayuzaur.foodies.block.crops;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.util.Identifier;

public class PotatoCrops extends BaseCrops{

    public PotatoCrops(Identifier identifier){
        super(identifier);
    }

    @Override
    protected Item getSeedItem() {
        return ItemListener.POTATO;
    }

    @Override
    protected int getSeedCount() {
        return 1;
    }

    @Override
    protected Item getCropItem() {
        return ItemListener.POTATO;
    }

    @Override
    protected int getCropCount() {
        return 3;
    }

    @Override
    protected int getCropChance() {
        return 5;
    }
}
