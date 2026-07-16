package ru.sanberdir.lesson1_21_4.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class ModRegion extends Region {

    public ModRegion() {
        super(
                ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "turquoise_valley"),
                RegionType.OVERWORLD,
                10
        );
    }

    @Override
    public void addBiomes (
            Registry<Biome> registry,
            Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {

        addBiomeSimilar(mapper, Biomes.PLAINS, ModBiomes.TURQUOISE_VALLEY);
        addBiomeSimilar(mapper, Biomes.FOREST, ModBiomes.TURQUOISE_VALLEY);
    }
}