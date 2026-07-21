package ru.sanberdir.lesson1_21_4.blocks.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Lesson1_21_4.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ModSignBlockEntity>> USUAL_SIGN =
            BLOCK_ENTITIES.register("usual_sign", () ->
                    new BlockEntityType<>(ModSignBlockEntity::new,
                            L1214Blocks.USUAL_SIGN.get(), L1214Blocks.USUAL_WALL_SIGN.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ModHangingSignBlockEntity>> USUAL_HANGING_SIGN =
            BLOCK_ENTITIES.register("usual_hanging_sign", () ->
                    new BlockEntityType<>(ModHangingSignBlockEntity::new,
                            L1214Blocks.USUAL_HANGING_SIGN.get(), L1214Blocks.USUAL_WALL_HANGING_SIGN.get()));
    public static final Supplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BE =
            BLOCK_ENTITIES.register("pedestal_be", () -> new BlockEntityType<>(
                    PedestalBlockEntity::new, L1214Blocks.PEDESTAL.get()));

    public static final Supplier<BlockEntityType<GrowthChamberBlockEntity>> GROWTH_CHAMBER_BE =
            BLOCK_ENTITIES.register("growth_chamber_be", () -> new BlockEntityType<>(
                    GrowthChamberBlockEntity::new, L1214Blocks.GROWTH_CHAMBER.get()));

}