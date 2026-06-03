package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.tags.ModTags;

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
                .add(L1214Blocks.USUAL_WALL.get())
                .add(L1214Blocks.USUAL_FENCE.get())
                .add(L1214Blocks.USUAL_FENCE_GATE.get())
                .add(L1214Blocks.USUAL_DOOR.get())
                .add(L1214Blocks.USUAL_TRAPDOOR.get())
                .add(L1214Blocks.USUAL_BUTTON.get())
                .add(L1214Blocks.USUAL_PRESSURE_PLATE.get())
                .add(L1214Blocks.USUAL_SLAB.get())
                .add(L1214Blocks.USUAL_STAIRS.get())
                .add(L1214Blocks.USUAL_WOOD.get())
                .add(L1214Blocks.STRIPPED_USUAL_LOG.get())
                .add(L1214Blocks.STRIPPED_USUAL_WOOD.get())
                .add(L1214Blocks.USUAL_LOG.get());
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(L1214Blocks.USUAL_LEAVES.get());
        tag(BlockTags.LEAVES)
                .add(L1214Blocks.USUAL_LEAVES.get());
        tag(BlockTags.PLANKS)
                .add(L1214Blocks.USUAL_PLANKS.get());
        tag(BlockTags.WALLS)
                .add(L1214Blocks.USUAL_WALL.get());
        tag(BlockTags.FENCES)
                .add(L1214Blocks.USUAL_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(L1214Blocks.USUAL_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS)
                .add(L1214Blocks.USUAL_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(L1214Blocks.USUAL_TRAPDOOR.get());
        tag(BlockTags.WOODEN_BUTTONS)
                .add(L1214Blocks.USUAL_BUTTON.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(L1214Blocks.USUAL_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_SLABS)
                .add(L1214Blocks.USUAL_SLAB.get());
        tag(BlockTags.WOODEN_STAIRS)
                .add(L1214Blocks.USUAL_STAIRS.get());
        tag(BlockTags.SAPLINGS)
                .add(L1214Blocks.USUAL_SAPLING.get());
        tag(ModBlockTags.USUAL_LOGS)
                .add(L1214Blocks.USUAL_LOG.get())
                .add(L1214Blocks.STRIPPED_USUAL_LOG.get())
                .add(L1214Blocks.USUAL_WOOD.get())
                .add(L1214Blocks.STRIPPED_USUAL_WOOD.get());
        tag(BlockTags.STANDING_SIGNS)
                .add(L1214Blocks.USUAL_SIGN.get());
        tag(BlockTags.WALL_SIGNS)
                .add(L1214Blocks.USUAL_WALL_SIGN.get());
        tag(BlockTags.ALL_SIGNS)
                .add(L1214Blocks.USUAL_SIGN.get())
                .add(L1214Blocks.USUAL_WALL_SIGN.get());
        tag(BlockTags.CEILING_HANGING_SIGNS)
                .add(L1214Blocks.USUAL_HANGING_SIGN.get());
        tag(BlockTags.WALL_HANGING_SIGNS)
                .add(L1214Blocks.USUAL_WALL_HANGING_SIGN.get());
        tag(BlockTags.ALL_HANGING_SIGNS)
                .add(L1214Blocks.USUAL_HANGING_SIGN.get())
                .add(L1214Blocks.USUAL_WALL_HANGING_SIGN.get());
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(L1214Blocks.USUAL_SIGN.get())
                .add(L1214Blocks.USUAL_WALL_SIGN.get())
                .add(L1214Blocks.USUAL_HANGING_SIGN.get())
                .add(L1214Blocks.USUAL_WALL_HANGING_SIGN.get());
        tag(BlockTags.LOGS_THAT_BURN)
                .addTags(ModBlockTags.USUAL_LOGS);
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(L1214Blocks.BISMUTH_DEEPSLATE_ORE.get())
                .add(L1214Blocks.MY_BETTER_BLOCK2.get());

        tag(ModTags.Blocks.INCORRECT_FOR_BISMUTH_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(L1214Blocks.BISMUTH_DEEPSLATE_ORE.get());
    }
}