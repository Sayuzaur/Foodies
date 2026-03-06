package io.github.sayuzaur.foodies.item.crops;

import net.modificationstation.stationapi.api.template.item.TemplateStackableFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Tomato extends TemplateStackableFoodItem{
    public Tomato(Identifier identifier) {
        super(identifier, 1, false, 64);
    }
}
