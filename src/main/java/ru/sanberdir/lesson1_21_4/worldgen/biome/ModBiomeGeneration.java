package ru.sanberdir.lesson1_21_4.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomeGeneration {

    public static Biome turquoiseValley(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7F)
                .downfall(1F)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0x1FAF7A)
                        .waterFogColor(0x075E3A)
                        .skyColor(0x78D6D0)
                        .fogColor(0x5FA9A5)
                        .grassColorOverride(0x4A8F6B)
                        .foliageColorOverride(0x2F6B4F)
                        .build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(generation.build())
                .build();
    }
}