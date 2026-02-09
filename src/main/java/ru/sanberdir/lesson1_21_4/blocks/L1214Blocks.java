package ru.sanberdir.lesson1_21_4.blocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.blocks.custom.ModFlammableRotatedPillarBlock;

public class L1214Blocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("lesson1_21_4");
    // Способ 1
    public static final DeferredBlock<Block> MY_BETTER_BLOCK = BLOCKS.register(
            "my_better_block",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName)).destroyTime(2.0f).explosionResistance(10.0f).sound(SoundType.BASALT)
            ));
    // Способ 2
    public static final DeferredBlock<Block> MY_BETTER_BLOCK2 = BLOCKS.registerBlock(
            "my_better_block2",
            Block::new,
            BlockBehaviour.Properties.of().destroyTime(2.0f).explosionResistance(10.0f).sound(SoundType.COPPER).requiresCorrectToolForDrops()
    );
    public static final DeferredBlock<Block> USUAL_PLANKS = BLOCKS.registerBlock(
            "usual_planks",
            Block::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)
                    .strength(1.0F, 3.0F).sound(SoundType.BAMBOO_WOOD).ignitedByLava()
    );

    // Брёвна
    public static final DeferredBlock<Block> USUAL_LOG = BLOCKS.registerBlock(
            "usual_log",
            ModFlammableRotatedPillarBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)
                    .strength(1.0F, 3.0F).sound(SoundType.BAMBOO_WOOD).ignitedByLava()
    );
    public static final DeferredBlock<Block> USUAL_WOOD = BLOCKS.registerBlock(
            "usual_wood",
            ModFlammableRotatedPillarBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)
                    .strength(1.0F, 3.0F).sound(SoundType.BAMBOO_WOOD).ignitedByLava()
    );
    public static final DeferredBlock<Block> STRIPPED_USUAL_LOG = BLOCKS.registerBlock(
            "stripped_usual_log",
            ModFlammableRotatedPillarBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)
                    .strength(1.0F, 3.0F).sound(SoundType.BAMBOO_WOOD).ignitedByLava()
    );
    public static final DeferredBlock<Block> STRIPPED_USUAL_WOOD = BLOCKS.registerBlock(
            "stripped_usual_wood",
            ModFlammableRotatedPillarBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)
                    .strength(1.0F, 3.0F).sound(SoundType.BAMBOO_WOOD).ignitedByLava()
    );
}
