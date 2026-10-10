package ru.sanberdir.lesson1_21_4.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Lesson1_21_4.MODID);

    public static final Supplier<SimpleParticleType> BISMUTH_PARTICLES =
            PARTICLE_TYPES.register("bismuth_particles", () -> new SimpleParticleType(true));
}