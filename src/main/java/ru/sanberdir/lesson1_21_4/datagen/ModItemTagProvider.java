package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.items.L1214Items;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, Lesson1_21_4.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.LOGS_THAT_BURN)
                .addTags(ModItemTags.USUAL_LOGS);
        tag(ModItemTags.USUAL_LOGS)
                .add(L1214Blocks.USUAL_LOG.get().asItem())
                .add(L1214Blocks.STRIPPED_USUAL_WOOD.get().asItem())
                .add(L1214Blocks.USUAL_WOOD.get().asItem())
                .add(L1214Blocks.STRIPPED_USUAL_LOG.get().asItem());
        tag(ItemTags.LEAVES)
                .add(L1214Blocks.USUAL_LEAVES.get().asItem());
        tag(ItemTags.PLANKS)
                .add(L1214Blocks.USUAL_PLANKS.get().asItem());
        tag(ItemTags.SAPLINGS)
                .add(L1214Blocks.USUAL_SAPLING.get().asItem());
        tag(ItemTags.WALLS)
                .add(L1214Blocks.USUAL_WALL.get().asItem());
        tag(ItemTags.FENCES)
                .add(L1214Blocks.USUAL_FENCE.get().asItem());
        tag(ItemTags.FENCE_GATES)
                .add(L1214Blocks.USUAL_FENCE_GATE.get().asItem());
        tag(ItemTags.WOODEN_DOORS)
                .add(L1214Blocks.USUAL_DOOR.get().asItem());
        tag(ItemTags.SIGNS)
                .add(L1214Blocks.USUAL_SIGN.get().asItem());
        tag(ItemTags.WOODEN_TRAPDOORS)
                .add(L1214Blocks.USUAL_TRAPDOOR.get().asItem());
        tag(ItemTags.WOODEN_BUTTONS)
                .add(L1214Blocks.USUAL_BUTTON.get().asItem());
        tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(L1214Blocks.USUAL_PRESSURE_PLATE.get().asItem());
        tag(ItemTags.WOODEN_SLABS)
                .add(L1214Blocks.USUAL_SLAB.get().asItem());
        tag(ItemTags.HANGING_SIGNS)
                .add(L1214Blocks.USUAL_HANGING_SIGN.get().asItem());
        tag(ItemTags.WOODEN_STAIRS)
                .add(L1214Blocks.USUAL_STAIRS.get().asItem());
        tag(ModItemTags.CHARCOAL_RECIPE)
                .addTags(ItemTags.PLANKS)
                .addTags(ItemTags.LOGS_THAT_BURN)
                .add(Items.RESIN_BRICKS)
                .add(Items.RESIN_BLOCK)
                .add(Items.RESIN_BRICK);
    }
}