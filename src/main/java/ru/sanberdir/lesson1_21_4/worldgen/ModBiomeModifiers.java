package ru.sanberdir.lesson1_21_4.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.entity.ModEntities;
import ru.sanberdir.lesson1_21_4.worldgen.biome.ModBiomes;

import java.util.List;

public class ModBiomeModifiers {


    public static final ResourceKey<BiomeModifier> ADD_TREE_USUAL = registerKey("add_tree_usual");

    public static final ResourceKey<BiomeModifier> ADD_BISMUTH_ORE = registerKey("add_bismuth_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_BISMUTH_ORE = registerKey("add_nether_bismuth_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_BISMUTH_ORE = registerKey("add_end_bismuth_ore");
    public static final ResourceKey<BiomeModifier> SPAWN_GECKO = registerKey("spawn_gecko");
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_TREE_USUAL, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.TURQUOISE_VALLEY)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.USUAL_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_BISMUTH_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BISMUTH_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // Example for individual Biomes!
        // context.register(ADD_BISMUTH_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
        //         HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SAVANNA)),
        //         HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BISMUTH_ORE_PLACED_KEY)),
        //         GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_NETHER_BISMUTH_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NETHER_BISMUTH_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_END_BISMUTH_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_BISMUTH_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(SPAWN_GECKO, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SWAMP), biomes.getOrThrow(Biomes.SAVANNA),biomes.getOrThrow(ModBiomes.TURQUOISE_VALLEY)),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.GECKO.get(), 20, 1, 7))));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, name));
    }
}