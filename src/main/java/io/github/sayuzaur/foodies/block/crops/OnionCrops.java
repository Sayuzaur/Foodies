package io.github.sayuzaur.foodies.block.crops;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.util.Identifier;

public class OnionCrops extends BaseCrops{

    public OnionCrops(Identifier identifier){
        super(identifier);
    }

    @Override
    protected Item getSeedItem() {
        return ItemListener.ONION;
    }

    @Override
    protected int getSeedCount() {
        return 1;
    }

    @Override
    protected Item getCropItem() {
        return ItemListener.ONION;
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
