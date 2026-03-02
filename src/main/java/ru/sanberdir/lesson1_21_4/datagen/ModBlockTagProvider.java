package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output,
                               CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Lesson1_21_4.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(L1214Blocks.BISMUTH_BLOCK.get())
                .add(L1214Blocks.BISMUTH_ORE.get())
                .add(L1214Blocks.MY_BETTER_BLOCK.get())
                .add(L1214Blocks.MY_BETTER_BLOCK2.get())
                .add(L1214Blocks.BISMUTH_DEEPSLATE_ORE.get())
                .add(L1214Blocks.BISMUTH_NETHER_ORE.get())
                .add(L1214Blocks.BISMUTH_END_ORE.get());
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(L1214Blocks.USUAL_PLANKS.get())
                .add(L1214Blocks.USUAL_WOOD.get())
                .add(L1214Blocks.STRIPPED_USUAL_LOG.get())
                .add(L1214Blocks.STRIPPED_USUAL_WOOD.get())
                .add(L1214Blocks.USUAL_LOG.get());
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(L1214Blocks.USUAL_LEAVES.get());
        tag(BlockTags.LEAVES)
                .add(L1214Blocks.USUAL_LEAVES.get());
        tag(BlockTags.SAPLINGS)
                .add(L1214Blocks.USUAL_SAPLING.get());
        tag(ModBlockTags.USUAL_LOGS)
                .add(L1214Blocks.USUAL_LOG.get())
                .add(L1214Blocks.STRIPPED_USUAL_LOG.get())
                .add(L1214Blocks.USUAL_WOOD.get())
                .add(L1214Blocks.STRIPPED_USUAL_WOOD.get());
        tag(BlockTags.LOGS_THAT_BURN)
                .addTags(ModBlockTags.USUAL_LOGS);
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(L1214Blocks.BISMUTH_DEEPSLATE_ORE.get())
                .add(L1214Blocks.MY_BETTER_BLOCK2.get());
    }
}