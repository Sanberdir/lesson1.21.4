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
                        output.accept(L1214Items.NUGGET_OBSIDIAN.get());
                        output.accept(L1214Items.NUGGET_OBSIDIAN2.get());
                        // блоки
                        output.accept(L1214Items.MY_BETTER_BLOCK.get());
                        output.accept(L1214Items.MY_BETTER_BLOCK2.get());
                        output.accept(L1214Items.USUAL_PLANKS.get());
                    })
                    .build());
}