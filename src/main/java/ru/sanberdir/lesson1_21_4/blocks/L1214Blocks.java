package ru.sanberdir.lesson1_21_4.blocks;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.blocks.custom.ModFlammableRotatedPillarBlock;
import ru.sanberdir.lesson1_21_4.blocks.custom.ModSaplingBlock;
import ru.sanberdir.lesson1_21_4.worldgen.tree.ModTreeGrowers;

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

    public static final DeferredBlock<Block> USUAL_LEAVES = BLOCKS.registerBlock(
            "usual_leaves",
            props -> new LeavesBlock(props) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, net.minecraft.core.BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, net.minecraft.core.BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, net.minecraft.core.BlockPos pos, Direction direction) {
                    return 30;
                }
            },
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
    );

    // Саженец
    public static final DeferredBlock<Block> USUAL_SAPLING = BLOCKS.registerBlock(
            "usual_sapling",
            props -> new ModSaplingBlock(ModTreeGrowers.USUAL, props, () -> Blocks.NETHERRACK),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
    );


    public static final DeferredBlock<Block> BISMUTH_BLOCK = BLOCKS.registerBlock(
            "bismuth_block",
            Block::new,
            BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).instrument(NoteBlockInstrument.BANJO));
    // Regular bismuth ore
    public static final DeferredBlock<Block> BISMUTH_ORE = BLOCKS.registerBlock(
            "bismuth_ore",
            props -> new DropExperienceBlock(UniformInt.of(2, 4), props),
            BlockBehaviour.Properties.of().strength(3.0f).requiresCorrectToolForDrops().sound(SoundType.STONE).instrument(NoteBlockInstrument.BANJO)
    );
    // Deepslate bismuth ore
    public static final DeferredBlock<Block> BISMUTH_DEEPSLATE_ORE = BLOCKS.registerBlock(
            "bismuth_deepslate_ore",
            props -> new DropExperienceBlock(UniformInt.of(3, 6), props),
            BlockBehaviour.Properties.of().strength(4.0f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE).instrument(NoteBlockInstrument.BANJO)
    );

    // End bismuth ore
    public static final DeferredBlock<Block> BISMUTH_END_ORE = BLOCKS.registerBlock(
            "bismuth_end_ore",
            props -> new DropExperienceBlock(UniformInt.of(5, 9), props),
            BlockBehaviour.Properties.of().strength(7.0f).requiresCorrectToolForDrops().sound(SoundType.STONE).instrument(NoteBlockInstrument.BANJO)  // Adding stone sound as it wasn't specified in original
    );

    // Nether bismuth ore
    public static final DeferredBlock<Block> BISMUTH_NETHER_ORE = BLOCKS.registerBlock(
            "bismuth_nether_ore",
            props -> new DropExperienceBlock(UniformInt.of(1, 5), props),
            BlockBehaviour.Properties.of().strength(3.0f).requiresCorrectToolForDrops().sound(SoundType.NETHER_ORE).instrument(NoteBlockInstrument.BANJO)  // Using nether ore sound for authenticity
    );
}
