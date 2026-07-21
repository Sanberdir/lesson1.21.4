package ru.sanberdir.lesson1_21_4.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.blocks.custom.*;
import ru.sanberdir.lesson1_21_4.items.L1214Items;
import ru.sanberdir.lesson1_21_4.blocks.custom.ModCrops;
import ru.sanberdir.lesson1_21_4.sounds.ModSounds;
import ru.sanberdir.lesson1_21_4.worldgen.tree.ModTreeGrowers;
import ru.sanberdir.lesson1_21_4.worldgen.wood.ModWoodTypes;

import java.util.function.Function;

public class L1214Blocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("lesson1_21_4");

    public static final DeferredBlock<Block> MY_BETTER_BLOCK = registerBlock("my_better_block",
            (properties) -> new Block(properties.destroyTime(2.0f).explosionResistance(10.0f).sound(SoundType.BASALT)));

    public static final DeferredBlock<Block> MY_BETTER_BLOCK2 = registerBlock("my_better_block2",
            (properties) -> new Block(properties.destroyTime(2.0f).explosionResistance(10.0f)
                    .sound(SoundType.COPPER).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> USUAL_PLANKS = registerBlock("usual_planks",
            (properties) -> new Block(properties.mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)
                    .strength(1.0F, 3.0F).sound(ModSounds.MAGIC_BLOCK_SOUNDS).ignitedByLava()));

    // Брёвна
    public static final DeferredBlock<Block> USUAL_LOG = registerBlock("usual_log",
            (properties) -> new ModFlammableRotatedPillarBlock(
                    properties.instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.CHERRY_WOOD).ignitedByLava()));
    public static final DeferredBlock<Block> USUAL_WOOD = registerBlock("usual_wood",
            (properties) -> new  ModFlammableRotatedPillarBlock(
                    properties.mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)
                    .strength(1.0F, 3.0F).sound(SoundType.BAMBOO_WOOD).ignitedByLava()));
    public static final DeferredBlock<Block> STRIPPED_USUAL_LOG = registerBlock("stripped_usual_log",
            (properties) -> new  ModFlammableRotatedPillarBlock(
                    properties.mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)
                    .strength(1.0F, 3.0F).sound(SoundType.BAMBOO_WOOD).ignitedByLava()));
    public static final DeferredBlock<Block> STRIPPED_USUAL_WOOD = registerBlock( "stripped_usual_wood",
            (properties) -> new  ModFlammableRotatedPillarBlock(
                    properties.mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS)
                    .strength(1.0F, 3.0F).sound(SoundType.BAMBOO_WOOD).ignitedByLava()));

    public static final DeferredBlock<Block> USUAL_LEAVES = registerBlock(
            "usual_leaves",
            (properties) -> new LeavesBlock(properties.mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.CHERRY_LEAVES)
                    .noOcclusion().isValidSpawn(Blocks::ocelotOrParrot).ignitedByLava().pushReaction(PushReaction.DESTROY)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });
    public static final DeferredBlock<Block> GREEN_WHEAT = BLOCKS.registerBlock("green_wheat",
            (properties) -> new ModCrops (
                    properties.mapColor((state) -> (Integer)state
                                    .getValue(CropBlock.AGE) >= 6 ? MapColor.COLOR_YELLOW : MapColor.PLANT)
                            .noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> USUAL_SIGN = BLOCKS.registerBlock("usual_sign",
            (properties) -> new ModStandingSignBlock(
                    properties.noCollission().strength(1.0F).ignitedByLava().sound(SoundType.WOOD),
                    ModWoodTypes.USUAL));

    public static final DeferredBlock<Block> USUAL_WALL_SIGN = BLOCKS.registerBlock("usual_wall_sign",
            (properties) -> new ModWallSignBlock(
                    properties.noCollission().strength(1.0F).ignitedByLava().sound(SoundType.WOOD),
                    ModWoodTypes.USUAL));

    public static final DeferredBlock<Block> USUAL_HANGING_SIGN = BLOCKS.registerBlock("usual_hanging_sign",
            (properties) -> new ModHangingSignBlock(
                    properties.noCollission().strength(1.0F).ignitedByLava().sound(SoundType.HANGING_SIGN),
                    ModWoodTypes.USUAL));

    public static final DeferredBlock<Block> USUAL_WALL_HANGING_SIGN = BLOCKS.registerBlock("usual_wall_hanging_sign",
            (properties) -> new ModWallHangingSignBlock(
                    properties.noCollission().strength(1.0F).ignitedByLava().sound(SoundType.HANGING_SIGN),
                    ModWoodTypes.USUAL));
    // Саженец
    public static final DeferredBlock<Block> USUAL_SAPLING = registerBlock(
            "usual_sapling",
            (properties) -> new SaplingBlock(ModTreeGrowers.USUAL,
                    properties.mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak()
                            .sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<StairBlock> USUAL_STAIRS = registerBlock("usual_stairs",
            (properties) -> new StairBlock(L1214Blocks.USUAL_PLANKS.get().defaultBlockState(),
                    properties.strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));
    public static final DeferredBlock<SlabBlock> USUAL_SLAB = registerBlock("usual_slab",
            (properties) -> new SlabBlock(properties.strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<PressurePlateBlock> USUAL_PRESSURE_PLATE = registerBlock("usual_pressure_plate",
            (properties) -> new PressurePlateBlock(BlockSetType.ACACIA, properties.strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<ButtonBlock> USUAL_BUTTON = registerBlock("usual_button",
            (properties) -> new ButtonBlock(BlockSetType.ACACIA, 20, properties.strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noCollission()));

    public static final DeferredBlock<FenceBlock> USUAL_FENCE = registerBlock("usual_fence",
            (properties) -> new FenceBlock(properties.strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));
    public static final DeferredBlock<FenceGateBlock> USUAL_FENCE_GATE = registerBlock("usual_fence_gate",
            (properties) -> new FenceGateBlock(WoodType.ACACIA, properties.sound(SoundType.WOOD).strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<WallBlock> USUAL_WALL = registerBlock("usual_wall",
            (properties) -> new WallBlock(properties.strength(2f).sound(SoundType.BAMBOO).requiresCorrectToolForDrops()));

    public static final DeferredBlock<DoorBlock> USUAL_DOOR = registerBlock("usual_door",
            (properties) -> new DoorBlock(BlockSetType.ACACIA, properties.sound(SoundType.WOOD).strength(2f).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<TrapDoorBlock> USUAL_TRAPDOOR = registerBlock("usual_trapdoor",
            (properties) -> new TrapDoorBlock(BlockSetType.ACACIA, properties.sound(SoundType.WOOD).strength(2f).requiresCorrectToolForDrops().noOcclusion()));


    public static final DeferredBlock<Block> BISMUTH_BLOCK = registerBlock(
            "bismuth_block",
            (properties) -> new Block(
                    properties.strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST).instrument(NoteBlockInstrument.BANJO)));
    // Regular bismuth ore
    public static final DeferredBlock<Block> BISMUTH_ORE = registerBlock("bismuth_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2, 4),
                    properties.strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> BISMUTH_DEEPSLATE_ORE = registerBlock("bismuth_deepslate_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(3, 6),
                    properties.strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> BISMUTH_END_ORE = registerBlock("bismuth_end_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(5, 9),
                    properties.strength(7f).requiresCorrectToolForDrops().requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> BISMUTH_NETHER_ORE = registerBlock("bismuth_nether_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(1, 5),
                    properties.strength(3f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> STRANGE_PORTAL_BLOCK = registerBlock("strange_portal_block",
            (properties) -> new StrangePortalBlock(properties.noCollission().randomTicks().strength(-1.0F)
                    .sound(SoundType.GLASS).lightLevel((p_50884_) -> 11).pushReaction(PushReaction.BLOCK)));

    public static final DeferredBlock<Block> PEDESTAL = registerBlock("pedestal",
            (properties) -> new PedestalBlock(properties.noOcclusion()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        L1214Items.ITEMS.registerItem(name, (properties) -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }
}
