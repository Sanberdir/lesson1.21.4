package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
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
                .add(L1214Items.USUAL_LOG.get())
                .add(L1214Items.STRIPPED_USUAL_WOOD.get())
                .add(L1214Items.USUAL_WOOD.get())
                .add(L1214Items.STRIPPED_USUAL_LOG.get());
        tag(ItemTags.LEAVES)
                .add(L1214Items.USUAL_LEAVES.get());
        tag(ItemTags.PLANKS)
                .add(L1214Items.USUAL_LEAVES.get());
        tag(ItemTags.SAPLINGS)
                .add(L1214Items.USUAL_LEAVES.get());
        tag(ModItemTags.CHARCOAL_RECIPE)
                .addTags(ItemTags.PLANKS)
                .addTags(ItemTags.LOGS_THAT_BURN)
                .add(Items.RESIN_BRICKS)
                .add(Items.RESIN_BLOCK)
                .add(Items.RESIN_BRICK);

    }
}