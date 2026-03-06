package io.github.sayuzaur.foodies.block.crops;

import io.github.sayuzaur.foodies.events.init.ItemListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public abstract class BaseCrops
    extends TemplateBlock
    implements BlockTemplate {

    public static final IntProperty AGE;
    static {
        AGE = Properties.AGE_7;
    }

    public BaseCrops(Identifier identifier){
        super(identifier, Material.PLANT);
        this.setTickRandomly(true);
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
        this.setSoundGroup(DIRT_SOUND_GROUP);
        setDefaultState(getStateManager().getDefaultState().with(AGE, 0));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    protected abstract Item getSeedItem();

    protected abstract int getSeedCount();

    protected abstract Item getCropItem();

    protected abstract int getCropCount();

    protected abstract int getCropChance();

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getStateManager().getDefaultState().with(AGE, 0);
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    protected boolean canPlantOnTop(int id) {
        return id == Block.FARMLAND.id;
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return super.canPlaceAt(world, x, y, z)
                && this.canPlantOnTop(world.getBlockId(x, y - 1, z));
    }

    @Override
    public boolean canGrow(World world, int x, int y, int z) {
        return (world.getBrightness(x, y, z) >= 8 || world.hasSkyLight(x, y, z)) && this.canPlantOnTop(world.getBlockId(x, y - 1, z));
    }

    protected final void breakIfCannotGrow(World world, int x, int y, int z) {
        if (!this.canGrow(world, x, y, z)) {
            this.dropStacks(world, x, y, z, world.getBlockState(x, y, z));
            world.setBlock(x, y, z, 0);
        }
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);
        this.breakIfCannotGrow(world, x, y, z);
    }

    public void applyFullGrowth(World world, int x, int y, int z) {
        BlockState current = world.getBlockState(x, y, z);
        world.setBlockStateWithNotify(x, y, z, current.with(AGE, 7));
    }

    private float getAvailableMoisture(World world, int x, int y, int z) {
        float moisture = 1.0F;
        int var6 = world.getBlockId(x, y, z - 1);
        int var7 = world.getBlockId(x, y, z + 1);
        int var8 = world.getBlockId(x - 1, y, z);
        int var9 = world.getBlockId(x + 1, y, z);
        int var10 = world.getBlockId(x - 1, y, z - 1);
        int var11 = world.getBlockId(x + 1, y, z - 1);
        int var12 = world.getBlockId(x + 1, y, z + 1);
        int var13 = world.getBlockId(x - 1, y, z + 1);
        boolean var14 = var8 == this.id || var9 == this.id;
        boolean var15 = var6 == this.id || var7 == this.id;
        boolean var16 = var10 == this.id || var11 == this.id || var12 == this.id || var13 == this.id;

        for(int var17 = x - 1; var17 <= x + 1; ++var17) {
            for(int var18 = z - 1; var18 <= z + 1; ++var18) {
                int var19 = world.getBlockId(var17, y - 1, var18);
                float var20 = 0.0F;
                if (var19 == Block.FARMLAND.id) {
                    var20 = 1.0F;
                    if (world.getBlockMeta(var17, y - 1, var18) > 0) {
                        var20 = 3.0F;
                    }
                }

                if (var17 != x || var18 != z) {
                    var20 /= 4.0F;
                }

                moisture += var20;
            }
        }

        if (var16 || var14 && var15) {
            moisture /= 2.0F;
        }

        return moisture;
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        this.breakIfCannotGrow(world, x, y, z);

        if (world.getLightLevel(x, y + 1, z) >= 9) {
            BlockState state = world.getBlockState(x, y, z);
            int age = state.get(AGE);

            if (age < 7) {
                float moisture = this.getAvailableMoisture(world, x, y, z);
                if (random.nextInt((int)(100.0F / moisture)) == 0) {
                    ++age;
                    world.setBlockStateWithNotify(x, y, z, state.with(AGE, age));
                }
            }
        }
    }
    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck) {
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, int x, int y, int z, BlockState state, int meta) {
        dropStacks(world, x, y, z, state);

        super.afterBreak(world, player, x, y, z, state, meta);
    }

    public void dropStacks(World world, int x, int y, int z, BlockState state) {
        if (!world.isRemote) {
            int age = state.get(AGE);

            ItemStack baseStack = new ItemStack(getSeedItem(), getSeedCount());
            ItemEntity baseCropsItemEntity = new ItemEntity(world, x + 0.5f, y + 1.0f, z + 0.5f, baseStack);
            baseCropsItemEntity.pickupDelay = 10;
            world.spawnEntity(baseCropsItemEntity);

            if (age >= 7){
                for(int i = 0; i < getCropCount(); ++i) {
                    if (world.random.nextInt(10) <= getCropChance()) {

                        float varBase = 0.7F;
                        float varX = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                        float varY = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;
                        float varZ = world.random.nextFloat() * varBase + (1.0F - varBase) * 0.5F;

                        ItemStack stack = new ItemStack(getCropItem());
                        ItemEntity cropsItemEntity = new ItemEntity(world,((float)x + varX),((float)y + varY),((float)z + varZ), stack);
                        cropsItemEntity.pickupDelay = 10;
                        world.spawnEntity(cropsItemEntity);
                    }
                }
            }
        }
    }
}
