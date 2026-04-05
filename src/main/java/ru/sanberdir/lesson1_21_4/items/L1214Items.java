package ru.sanberdir.lesson1_21_4.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.items.custom.FuelItem;
import ru.sanberdir.lesson1_21_4.items.custom.ModBoatItem;
import ru.sanberdir.lesson1_21_4.items.entity.ModBoatEntityUsual;

public class L1214Items {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Lesson1_21_4.MODID);

    public static final DeferredItem<Item> NUGGET_OBSIDIAN = ITEMS.registerItem("nugget_obsidian",
            properties -> new Item(properties),
            new Item.Properties().food(new FoodProperties(12,0.5F,false)));

    public static final DeferredItem<Item> BISMUTH = ITEMS.registerItem("bismuth",
            properties -> new Item(properties),
            new Item.Properties());
    public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.registerItem("raw_bismuth",
            properties -> new Item(properties),
            new Item.Properties());

    public static final DeferredItem<Item> NUGGET_OBSIDIAN2 = ITEMS.registerItem("nugget_obsidian2",
            properties -> new FuelItem(properties,1200),
            new Item.Properties());

    public static final DeferredItem<Item> USUAL_SIGN_ITEM = ITEMS.registerItem("usual_sign",
            properties -> new SignItem(
                    L1214Blocks.USUAL_SIGN.get(),
                    L1214Blocks.USUAL_WALL_SIGN.get(),
                    properties));

    public static final DeferredItem<Item> USUAL_HANGING_SIGN_ITEM = ITEMS.registerItem("usual_hanging_sign",
            properties -> new HangingSignItem(
                    L1214Blocks.USUAL_HANGING_SIGN.get(),
                    L1214Blocks.USUAL_WALL_HANGING_SIGN.get(),
                    properties));

    public static final DeferredItem<Item> USUAL_BOAT = ITEMS.registerItem("usual_boat",
            properties -> new ModBoatItem(false, ModBoatEntityUsual.Type.USUAL, properties),
            new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> USUAL_CHEST_BOAT = ITEMS.registerItem("usual_chest_boat",
            properties -> new ModBoatItem(true, ModBoatEntityUsual.Type.USUAL, properties),
            new Item.Properties().stacksTo(1));
}
