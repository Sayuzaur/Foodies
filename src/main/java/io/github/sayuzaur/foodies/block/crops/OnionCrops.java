/*
 * Copyright (C) 2026 Sayuzaur
 *
 * This file is part of Foodies.
 * Foodies is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Foodies is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with Foodies.
 * If not, see <https://www.gnu.org/licenses/>.
 */

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
