package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.items.L1214Items;

import java.util.List;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        // Блок из 9 слитков
        shaped(RecipeCategory.MISC, L1214Blocks.BISMUTH_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', L1214Items.BISMUTH.get())
                .unlockedBy(getHasName(L1214Items.BISMUTH.get()), has(L1214Items.BISMUTH.get()))
                .save(output);


        shaped(RecipeCategory.TRANSPORTATION, L1214Items.USUAL_BOAT.get())
                .pattern("P P")
                .pattern("PPP")
                .define('P', L1214Blocks.USUAL_PLANKS.get())
                .unlockedBy("has_usual_planks", has(L1214Blocks.USUAL_PLANKS.get()))
                .save(output);

// Лодка с сундуком
        shaped(RecipeCategory.TRANSPORTATION, L1214Items.USUAL_CHEST_BOAT.get())
                .pattern("C")
                .pattern("B")
                .define('C', Blocks.CHEST)
                .define('B', L1214Items.USUAL_BOAT.get())
                .unlockedBy("has_usual_boat", has(L1214Items.USUAL_BOAT.get()))
                .save(output);

        stairBuilder(L1214Blocks.USUAL_STAIRS.get(), Ingredient.of(L1214Blocks.USUAL_PLANKS)).group("usual")
                .unlockedBy("has_usual", has(L1214Blocks.USUAL_PLANKS)).save(output);
        slab(RecipeCategory.BUILDING_BLOCKS, L1214Blocks.USUAL_SLAB.get(), L1214Blocks.USUAL_PLANKS.get());

        buttonBuilder(L1214Blocks.USUAL_BUTTON.get(), Ingredient.of(L1214Blocks.USUAL_PLANKS.get())).group("usual")
                .unlockedBy("has_usual", has(L1214Blocks.USUAL_PLANKS.get())).save(output);
        pressurePlate(L1214Blocks.USUAL_PRESSURE_PLATE.get(), L1214Blocks.USUAL_PLANKS.get());

        fenceBuilder(L1214Blocks.USUAL_FENCE.get(), Ingredient.of(L1214Blocks.USUAL_PLANKS.get())).group("usual")
                .unlockedBy("has_usual", has(L1214Blocks.USUAL_PLANKS.get())).save(output);
        fenceGateBuilder(L1214Blocks.USUAL_FENCE_GATE.get(), Ingredient.of(L1214Blocks.USUAL_PLANKS.get())).group("usual")
                .unlockedBy("has_usual", has(L1214Blocks.USUAL_PLANKS.get())).save(output);
        wall(RecipeCategory.BUILDING_BLOCKS, L1214Blocks.USUAL_WALL.get(), L1214Blocks.USUAL_PLANKS.get());

        doorBuilder(L1214Blocks.USUAL_DOOR.get(), Ingredient.of(L1214Blocks.USUAL_PLANKS.get())).group("usual")
                .unlockedBy("has_usual", has(L1214Blocks.USUAL_PLANKS.get())).save(output);
        trapdoorBuilder(L1214Blocks.USUAL_TRAPDOOR.get(), Ingredient.of(L1214Blocks.USUAL_PLANKS.get())).group("usual")
                .unlockedBy("has_usual", has(L1214Blocks.USUAL_PLANKS.get())).save(output);
        // 9 слитков из блока
        shapeless(RecipeCategory.MISC, L1214Items.BISMUTH.get(), 9)
                .requires(L1214Blocks.BISMUTH_BLOCK.get())
                .unlockedBy(getHasName(L1214Blocks.BISMUTH_BLOCK.get()), has(L1214Blocks.BISMUTH_BLOCK.get()))
                .save(output);
// Пример рецепта с тегом
        shapeless(RecipeCategory.MISC, L1214Blocks.USUAL_PLANKS, 4)
                .requires(ItemTags.create(ResourceLocation.tryParse("minecraft:usual_logs")))
                .unlockedBy("has_planks", has(ItemTags.create(ResourceLocation.tryParse("minecraft:usual_logs"))))
                .save(output);

        // Плавка
        List<ItemLike> ores = List.of(
                L1214Items.RAW_BISMUTH.get(),
                L1214Blocks.BISMUTH_ORE.get(),
                L1214Blocks.BISMUTH_NETHER_ORE.get(),
                L1214Blocks.BISMUTH_END_ORE.get(),
                L1214Blocks.BISMUTH_DEEPSLATE_ORE.get()
        );
// Обычная табличка
        signBuilder(L1214Blocks.USUAL_SIGN.get(), Ingredient.of(L1214Blocks.USUAL_PLANKS.get()))
                .unlockedBy("has_planks", has(L1214Blocks.USUAL_PLANKS.get()))
                .save(output);

// Висячая табличка
        hangingSign(L1214Blocks.USUAL_HANGING_SIGN.get(), L1214Blocks.STRIPPED_USUAL_LOG.get());


        oreSmelting(ores, RecipeCategory.MISC, L1214Items.BISMUTH.get(), 0.25f, 200, "bismuth");
        oreBlasting(ores, RecipeCategory.MISC, L1214Items.BISMUTH.get(), 0.25f, 100, "bismuth");
    }
}