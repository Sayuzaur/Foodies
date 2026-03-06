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
