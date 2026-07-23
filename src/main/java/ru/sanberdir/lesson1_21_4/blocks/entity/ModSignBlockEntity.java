package ru.sanberdir.lesson1_21_4.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModSignBlockEntity extends SignBlockEntity {
    public ModSignBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.USUAL_SIGN.get(), pos, state); // <- обязательно
    }


    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.USUAL_SIGN.get();
    }
}