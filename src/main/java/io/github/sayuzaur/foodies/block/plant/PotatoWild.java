/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.block.plant;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class PotatoWild extends BaseWildCrop {
    public PotatoWild(Identifier identifier) {
        super(identifier);
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return ItemListener.POTATO.id;
    }
}