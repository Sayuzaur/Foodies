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

package io.github.sayuzaur.foodies.block.plant;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class CarrotWild extends BaseWildCrop {
    public CarrotWild(Identifier identifier) {
        super(identifier);
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return ItemListener.CARROT.id;
    }
}