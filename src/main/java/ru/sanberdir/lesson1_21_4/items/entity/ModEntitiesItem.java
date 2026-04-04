package ru.sanberdir.lesson1_21_4.items.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;

public class ModEntitiesItem {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Lesson1_21_4.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<ModBoatEntityUsual>> MOD_BOAT_USUAL =
            ENTITIES.register("mod_boat_usual", () ->
                    EntityType.Builder.<ModBoatEntityUsual>of(ModBoatEntityUsual::new, MobCategory.MISC)
                            .sized(1.375f, 0.5625f)
                            .build(ResourceKey.create(
                                    BuiltInRegistries.ENTITY_TYPE.key(),
                                    ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "mod_boat_usual")
                            )));

    public static final DeferredHolder<EntityType<?>, EntityType<ModChestBoatEntityUsual>> MOD_CHEST_BOAT_USUAL =
            ENTITIES.register("mod_chest_boat_usual", () ->
                    EntityType.Builder.<ModChestBoatEntityUsual>of(ModChestBoatEntityUsual::new, MobCategory.MISC)
                            .sized(1.375f, 0.5625f)
                            .build(ResourceKey.create(
                                    BuiltInRegistries.ENTITY_TYPE.key(),
                                    ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "mod_chest_boat_usual")
                            )));
}