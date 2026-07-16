package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.trim.ModTrimMaterials;
import ru.sanberdir.lesson1_21_4.trim.ModTrimPatterns;
import ru.sanberdir.lesson1_21_4.worldgen.ModBiomeModifiers;
import ru.sanberdir.lesson1_21_4.worldgen.ModConfiguredFeatures;
import ru.sanberdir.lesson1_21_4.worldgen.ModPlacedFeatures;
import ru.sanberdir.lesson1_21_4.worldgen.biome.ModBiomes;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Lesson1_21_4.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    // Наш BUILDER для NeoForge DatapackBuiltinEntriesProvider
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(Registries.TRIM_MATERIAL, ModTrimMaterials::bootstrap)
            .add(Registries.TRIM_PATTERN, ModTrimPatterns::bootstrap)
            .add(Registries.BIOME, ModBiomes::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);


    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();

        PackOutput output = generator.getPackOutput();

        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Обязательно передаем свой BUILDER и свой modid
        generator.addProvider(true, new DatapackBuiltinEntriesProvider(
                output, lookupProvider,BUILDER,Set.of(Lesson1_21_4.MODID)));

        generator.addProvider(true, new LootTableProvider(output,Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new,LootContextParamSets.BLOCK)),lookupProvider));


        var blockTags = new ModBlockTagProvider(output, lookupProvider);
        generator.addProvider(true, blockTags);

        var itemTags = new ModItemTagProvider(output,lookupProvider,blockTags.contentsGetter());
        generator.addProvider(true, itemTags);
        generator.addProvider(true, new ModModelProvider(output));
        generator.addProvider(true, new ModRecipeProvider.Runner(output, lookupProvider) {
            @Override
            public @NotNull String getName() {
                return "";
            }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
                return new ModRecipeProvider(registries, output);
            }
        });
    }
}