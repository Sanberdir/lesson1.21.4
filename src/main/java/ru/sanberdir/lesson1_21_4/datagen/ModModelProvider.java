package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.PackOutput;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.items.L1214Items;

import java.util.Optional;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, Lesson1_21_4.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(L1214Items.BISMUTH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(L1214Items.RAW_BISMUTH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(L1214Items.NUGGET_OBSIDIAN2.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(L1214Items.NUGGET_OBSIDIAN.get(), ModelTemplates.FLAT_ITEM);



        /* BLOCKS */
        blockModels.createTrivialCube(L1214Blocks.BISMUTH_BLOCK.get());
        blockModels.createTrivialCube(L1214Blocks.BISMUTH_ORE.get());
        blockModels.createTrivialCube(L1214Blocks.BISMUTH_DEEPSLATE_ORE.get());
        blockModels.createTrivialCube(L1214Blocks.BISMUTH_END_ORE.get());
        blockModels.createTrivialCube(L1214Blocks.BISMUTH_NETHER_ORE.get());
        blockModels.createTrivialCube(L1214Blocks.MY_BETTER_BLOCK2.get());
        blockModels.createTrivialCube(L1214Blocks.MY_BETTER_BLOCK.get());


        blockModels.family(L1214Blocks.USUAL_PLANKS.get())

                .fence(L1214Blocks.USUAL_FENCE.get())
                .fenceGate(L1214Blocks.USUAL_FENCE_GATE.get())
                .wall(L1214Blocks.USUAL_WALL.get())
                .stairs(L1214Blocks.USUAL_STAIRS.get())
                .slab(L1214Blocks.USUAL_SLAB.get())
                .button(L1214Blocks.USUAL_BUTTON.get())
                .pressurePlate(L1214Blocks.USUAL_PRESSURE_PLATE.get())
                .door(L1214Blocks.USUAL_DOOR.get())
                .trapdoor(L1214Blocks.USUAL_TRAPDOOR.get());

        blockModels.woodProvider(L1214Blocks.USUAL_LOG.get()).logWithHorizontal(L1214Blocks.USUAL_LOG.get()).wood(L1214Blocks.USUAL_WOOD.get());
        blockModels.woodProvider(L1214Blocks.STRIPPED_USUAL_LOG.get()).logWithHorizontal(L1214Blocks.STRIPPED_USUAL_LOG.get()).wood(L1214Blocks.STRIPPED_USUAL_WOOD.get());


        blockModels.createTintedLeaves(L1214Blocks.USUAL_LEAVES.get(), TexturedModel.LEAVES, -12012264);

        blockModels.createCrossBlock(L1214Blocks.USUAL_SAPLING.get(), BlockModelGenerators.PlantType.TINTED);

        itemModels.generateFlatItem(L1214Blocks.USUAL_SAPLING.get().asItem(), ModelTemplates.FLAT_ITEM);


    }


}