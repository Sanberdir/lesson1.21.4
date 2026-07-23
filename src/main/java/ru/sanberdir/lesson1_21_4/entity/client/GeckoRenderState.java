package ru.sanberdir.lesson1_21_4.entity.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import ru.sanberdir.lesson1_21_4.entity.custom.GeckoVariant;

public class GeckoRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public GeckoVariant variant;
}