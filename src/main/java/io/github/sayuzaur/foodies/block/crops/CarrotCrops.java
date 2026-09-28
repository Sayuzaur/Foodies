/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block.crops;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.util.Identifier;

public class CarrotCrops extends BaseCrops {

    public CarrotCrops(Identifier identifier){
        super(identifier);
    }

    @Override
    protected Item getSeedItem() {
        return ItemListener.CARROT;
    }

    @Override
    protected int getBonusSeedCount() {
        return 0;
    }

    @Override
    protected int getBonusSeedChance() {
        return 0;
    }

    @Override
    protected Item getCropItem() {
        return ItemListener.CARROT;
    }

    @Override
    protected int getCropCount() {
        return 1;
    }

    @Override
    protected int getBonusCropCount() {
        return 3;
    }

    @Override
    protected int getBonusCropChance() {
        return 3;
    }
}
