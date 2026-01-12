package ru.sanberdir.lesson1_21_4.items;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;

public class L1214Items {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Lesson1_21_4.MODID);

    public static final DeferredItem<Item> NUGGET_OBSIDIAN = ITEMS.registerItem("nugget_obsidian",
            Item::new,  new Item.Properties());
}
