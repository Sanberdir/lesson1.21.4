package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.items.L1214Items;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(L1214Blocks.BISMUTH_BLOCK.get());
        // dropSelf(L1214Blocks.MAGIC_BLOCK.get());

        add(L1214Blocks.BISMUTH_ORE.get(),
                block -> createOreDrop(L1214Blocks.BISMUTH_ORE.get(), L1214Items.RAW_BISMUTH.get()));
        add(L1214Blocks.BISMUTH_DEEPSLATE_ORE.get(),
                block -> createMultipleOreDrops(L1214Blocks.BISMUTH_DEEPSLATE_ORE.get(), L1214Items.RAW_BISMUTH.get(), 1, 4));

        add(L1214Blocks.BISMUTH_END_ORE.get(),
                block -> createMultipleOreDrops(L1214Blocks.BISMUTH_END_ORE.get(), L1214Items.RAW_BISMUTH.get(), 1, 6));
        add(L1214Blocks.BISMUTH_NETHER_ORE.get(),
                block -> createMultipleOreDrops(L1214Blocks.BISMUTH_NETHER_ORE.get(), L1214Items.RAW_BISMUTH.get(), 1, 8));
        dropSelf(L1214Blocks.MY_BETTER_BLOCK.get());
        dropSelf(L1214Blocks.MY_BETTER_BLOCK2.get());
        dropSelf(L1214Blocks.USUAL_LOG.get());
        dropSelf(L1214Blocks.STRIPPED_USUAL_LOG.get());
        dropSelf(L1214Blocks.USUAL_WOOD.get());
        dropSelf(L1214Blocks.STRIPPED_USUAL_WOOD.get());
        dropSelf(L1214Blocks.USUAL_PLANKS.get());

        dropSelf(L1214Blocks.USUAL_FENCE.get());
        dropSelf(L1214Blocks.USUAL_FENCE_GATE.get());
        dropSelf(L1214Blocks.USUAL_WALL.get());
        dropSelf(L1214Blocks.USUAL_TRAPDOOR.get());
        dropSelf(L1214Blocks.USUAL_PRESSURE_PLATE.get());
        dropSelf(L1214Blocks.USUAL_BUTTON.get());
        dropSelf(L1214Blocks.USUAL_STAIRS.get());
        add(L1214Blocks.USUAL_SLAB.get(),
                block -> createSlabItemTable(L1214Blocks.USUAL_SLAB.get()));
        add(L1214Blocks.USUAL_DOOR.get(),
                block -> createDoorTable(L1214Blocks.USUAL_DOOR.get()));
        dropSelf(L1214Blocks.USUAL_SAPLING.get());
        add(L1214Blocks.USUAL_LEAVES.get(),
                block -> createBlazeLeavesDrops(
                        L1214Blocks.USUAL_LEAVES.get(),
                        L1214Blocks.USUAL_SAPLING.get(),
                        NORMAL_LEAVES_SAPLING_CHANCES
                ));
    }
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

    private LootItemCondition.Builder hasShearsOrSilkTouch() {
        return this.hasShears().or(this.hasSilkTouch());
    }
    private LootItemCondition.Builder doesNotHaveShearsOrSilkTouch() {
        return this.hasShearsOrSilkTouch().invert();
    }
    protected LootTable.Builder createBlazeLeavesDrops(Block leavesBlock, Block saplingBlock, float... chances) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup =
                this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        return this.createSilkTouchOrShearsDispatchTable(
                leavesBlock,
                ((LootPoolSingletonContainer.Builder)
                        this.applyExplosionCondition(leavesBlock, LootItem.lootTableItem(saplingBlock)))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                registrylookup.getOrThrow(Enchantments.FORTUNE), chances))
        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(this.doesNotHaveShearsOrSilkTouch()).add(((LootPoolSingletonContainer.Builder)
                                this.applyExplosionDecay(leavesBlock,LootItem.lootTableItem(Items.BLAZE_ROD).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE),this.NORMAL_LEAVES_STICK_CHANCES))
                        )
        );
    }
    public LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return L1214Blocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}