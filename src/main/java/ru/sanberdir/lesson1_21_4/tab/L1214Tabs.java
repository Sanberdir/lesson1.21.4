package ru.sanberdir.lesson1_21_4.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.items.L1214Items;

import java.util.function.Supplier;

public class L1214Tabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "lesson1_21_4"); // MOD_ID вашего мода

    // Регистрируем вкладку
    public static final Supplier<CreativeModeTab> ITEMS_NEO_TAB = CREATIVE_MODE_TABS.register("items_neo",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.lesson1_21_4.items_neo"))
                    .icon(() -> new ItemStack(L1214Items.NUGGET_OBSIDIAN.get()))
                    .displayItems((params, output) -> {
                        // предметы
                        output.accept(L1214Items.NUGGET_OBSIDIAN);
                        output.accept(L1214Items.NUGGET_OBSIDIAN2);
                        output.accept(L1214Items.BISMUTH);
                        output.accept(L1214Items.RAW_BISMUTH);

                        // блоки
                        output.accept(L1214Blocks.MY_BETTER_BLOCK);
                        output.accept(L1214Blocks.MY_BETTER_BLOCK2);
                        output.accept(L1214Blocks.USUAL_PLANKS);
                        output.accept(L1214Blocks.USUAL_WOOD);
                        output.accept(L1214Blocks.USUAL_LOG);
                        output.accept(L1214Blocks.STRIPPED_USUAL_WOOD);
                        output.accept(L1214Blocks.STRIPPED_USUAL_LOG);
                        output.accept(L1214Blocks.USUAL_LEAVES);
                        output.accept(L1214Blocks.USUAL_SAPLING);
                        output.accept(L1214Blocks.USUAL_BUTTON);
                        output.accept(L1214Blocks.USUAL_STAIRS);
                        output.accept(L1214Blocks.USUAL_SLAB);
                        output.accept(L1214Blocks.USUAL_FENCE);
                        output.accept(L1214Blocks.USUAL_FENCE_GATE);
                        output.accept(L1214Blocks.USUAL_PRESSURE_PLATE);
                        output.accept(L1214Blocks.USUAL_DOOR);
                        output.accept(L1214Blocks.USUAL_WALL);
                        output.accept(L1214Blocks.USUAL_TRAPDOOR);

                        output.accept(L1214Blocks.BISMUTH_BLOCK);
                        output.accept(L1214Blocks.BISMUTH_ORE);
                        output.accept(L1214Blocks.BISMUTH_DEEPSLATE_ORE);
                        output.accept(L1214Blocks.BISMUTH_NETHER_ORE);
                        output.accept(L1214Blocks.BISMUTH_END_ORE);

                        output.accept(L1214Blocks.USUAL_SIGN.get());
                        output.accept(L1214Blocks.USUAL_HANGING_SIGN.get());
                    })
                    .build());
}