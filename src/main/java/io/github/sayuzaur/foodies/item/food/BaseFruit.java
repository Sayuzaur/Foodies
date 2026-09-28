/*
 * Copyright (c) 2026 Sayuzaur
 * Licensed under the EUPL-1.2-or-later.
 */

package io.github.sayuzaur.foodies.item.food;

import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BaseFruit extends TemplateStackableFoodItem {
    public BaseFruit(Identifier identifier) {
        //TODO Add config
        super(identifier, 2, false, 8);
    }
}