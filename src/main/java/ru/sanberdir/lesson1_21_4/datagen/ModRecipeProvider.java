package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
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

        // 9 слитков из блока
        shapeless(RecipeCategory.MISC, L1214Items.BISMUTH.get(), 9)
                .requires(L1214Blocks.BISMUTH_BLOCK.get())
                .unlockedBy(getHasName(L1214Blocks.BISMUTH_BLOCK.get()), has(L1214Blocks.BISMUTH_BLOCK.get()))
                .save(output);
// Пример рецепта с тегом
        shapeless(RecipeCategory.MISC, L1214Items.USUAL_PLANKS, 4)
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

        oreSmelting(ores, RecipeCategory.MISC, L1214Items.BISMUTH.get(), 0.25f, 200, "bismuth");
        oreBlasting(ores, RecipeCategory.MISC, L1214Items.BISMUTH.get(), 0.25f, 100, "bismuth");
    }
}