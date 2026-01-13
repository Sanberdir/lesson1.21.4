package ru.sanberdir.lesson1_21_4.blocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class L1214Blocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("lesson1_21_4");
    // Способ 1
    public static final DeferredBlock<Block> MY_BETTER_BLOCK = BLOCKS.register(
            "my_better_block",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName)).destroyTime(2.0f).explosionResistance(10.0f).sound(SoundType.GRAVEL)
                    .lightLevel(state -> 7)
            ));
    // Способ 2
    public static final DeferredBlock<Block> MY_BETTER_BLOCK2 = BLOCKS.registerBlock(
            "my_better_block2",
            Block::new,
            BlockBehaviour.Properties.of().destroyTime(2.0f).explosionResistance(10.0f).sound(SoundType.GRAVEL)
    );
}
