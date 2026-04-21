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

package io.github.sayuzaur.foodies.block;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class SaltOre extends TemplateBlock {
    public SaltOre(Identifier identifier){
        super(identifier, Material.STONE);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setSoundGroup(STONE_SOUND_GROUP);
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return ItemListener.SALT.id;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return 4 + random.nextInt(3);
    }
}

