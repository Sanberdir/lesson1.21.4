package ru.sanberdir.lesson1_21_4.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import ru.sanberdir.lesson1_21_4.items.L1214Items;

public class ModCrops extends CropBlock {
    public ModCrops(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int age = state.getValue(AGE);
        if (age < 7) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()) {
            level.setBlock(pos, state.setValue(AGE, 3), 3);
            popResource(level, pos, new ItemStack(Items.WHEAT));
            popResource(level, pos, new ItemStack(L1214Items.GREEN_WHEAT.get()));
        }

        return InteractionResult.SUCCESS;
    }
}