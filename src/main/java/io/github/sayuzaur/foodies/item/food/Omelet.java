package io.github.sayuzaur.foodies.item.food;

import net.modificationstation.stationapi.api.template.item.TemplateFoodItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Omelet extends TemplateFoodItem {
    public Omelet(Identifier identifier){
        super(identifier, 6, false);
    }
}
