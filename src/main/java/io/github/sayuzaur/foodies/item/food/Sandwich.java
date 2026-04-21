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

package io.github.sayuzaur.foodies.item.food;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.api.util.Identifier;

public class Sandwich extends TemplateStackableFoodItem implements CustomTooltipProvider {
    public static final String[] tooltipContent = new String[]{
            "air",
            "meat and veggies",
            "meat and cheese",
            "meat and egg",
            "fish and veggies",
            "fish and cheese",
            "fish and egg",
            "fish and meat",
            "cheese and veggies",
            "cheese and egg",
            "veggies and egg"
    };

    public Sandwich(Identifier identifier){
        super (identifier, 7, false, 2);
        //this.setHasSubtypes(true);
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        return new String[]{originalTooltip,
                Formatting.GRAY + "with " + tooltipContent[stack.getDamage()]};
    }
}
