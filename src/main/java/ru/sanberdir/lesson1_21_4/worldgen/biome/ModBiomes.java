package ru.sanberdir.lesson1_21_4.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;

public class ModBiomes {

    public static final ResourceKey<Biome> TURQUOISE_VALLEY = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "turquoise_valley"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(TURQUOISE_VALLEY, ModBiomeGeneration.turquoiseValley(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER)
        ));
    }
}