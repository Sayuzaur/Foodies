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

