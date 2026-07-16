package ru.sanberdir.lesson1_21_4.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public interface OverworldBiomeBuilderAccessor {
    @Invoker("addBiomes")
    void invokeAddBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer);
}