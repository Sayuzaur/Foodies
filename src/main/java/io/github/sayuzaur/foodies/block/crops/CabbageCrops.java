package io.github.sayuzaur.foodies.block.crops;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.util.Identifier;

public class CabbageCrops extends BaseCrops{

    public CabbageCrops(Identifier identifier){
        super(identifier);
    }

    @Override
    protected Item getSeedItem() {
        return ItemListener.CABBAGE_SEEDS;
    }

    @Override
    protected int getSeedCount() {
        return 1;
    }

    @Override
    protected Item getCropItem() {
        return ItemListener.CABBAGE;
    }

    @Override
    protected int getCropCount() {
        return 1;
    }

    @Override
    protected int getCropChance() {
        return 9;
    }
}
