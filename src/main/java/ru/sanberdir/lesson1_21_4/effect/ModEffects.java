package ru.sanberdir.lesson1_21_4.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Lesson1_21_4.MODID);

    public static final Holder<MobEffect> SLIMEY_EFFECT = MOB_EFFECTS.register("slimey",
            () -> new SlimeyEffect(MobEffectCategory.NEUTRAL, 0x36ebab)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "slimey"), -0.25f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
}