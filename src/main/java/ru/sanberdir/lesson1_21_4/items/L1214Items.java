package ru.sanberdir.lesson1_21_4.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.items.custom.FuelItem;
import ru.sanberdir.lesson1_21_4.items.custom.ModArmorItem;
import ru.sanberdir.lesson1_21_4.items.custom.ModBoatItem;
import ru.sanberdir.lesson1_21_4.items.entity.ModBoatEntityUsual;
import ru.sanberdir.lesson1_21_4.sounds.ModSounds;

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
    public static final DeferredItem<Item> GREEN_WHEAT = ITEMS.registerItem("green_wheat",
            properties -> new BlockItem(
                    L1214Blocks.GREEN_WHEAT.get(),
                    properties));
    public static final DeferredItem<Item> USUAL_BOAT = ITEMS.registerItem("usual_boat",
            properties -> new ModBoatItem(false, ModBoatEntityUsual.Type.USUAL, properties),
            new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> USUAL_CHEST_BOAT = ITEMS.registerItem("usual_chest_boat",
            properties -> new ModBoatItem(true, ModBoatEntityUsual.Type.USUAL, properties),
            new Item.Properties().stacksTo(1));

    public static final DeferredItem<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.registerItem("bar_brawl_music_disc",
            properties -> new Item(properties.jukeboxPlayable(ModSounds.BAR_BRAWL_KEY).stacksTo(1)));

    public static final DeferredItem<SwordItem> BISMUTH_SWORD = ITEMS.registerItem("bismuth_sword",
            properties -> new SwordItem(ModToolTiers.BISMUTH, 5.0F, -2.4f, properties));
    public static final DeferredItem<PickaxeItem> BISMUTH_PICKAXE = ITEMS.registerItem("bismuth_pickaxe",
            properties -> new PickaxeItem(ModToolTiers.BISMUTH, 1.0F, -2.8f, properties));
    public static final DeferredItem<ShovelItem> BISMUTH_SHOVEL = ITEMS.registerItem("bismuth_shovel",
            properties -> new ShovelItem(ModToolTiers.BISMUTH, 1.5F, -3.0f, properties));
    public static final DeferredItem<AxeItem> BISMUTH_AXE = ITEMS.registerItem("bismuth_axe",
            properties -> new AxeItem(ModToolTiers.BISMUTH, 6.0F, -3.2f, properties));
    public static final DeferredItem<HoeItem> BISMUTH_HOE = ITEMS.registerItem("bismuth_hoe",
            properties -> new HoeItem(ModToolTiers.BISMUTH, 0F, -3.0f, properties));

    public static final DeferredItem<ArmorItem> BISMUTH_HELMET = ITEMS.registerItem("bismuth_helmet",
            properties -> new ModArmorItem(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, ArmorType.HELMET, properties));
    public static final DeferredItem<ArmorItem> BISMUTH_CHESTPLATE = ITEMS.registerItem("bismuth_chestplate",
            properties -> new ArmorItem(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<ArmorItem> BISMUTH_LEGGINGS = ITEMS.registerItem("bismuth_leggings",
            properties -> new ArmorItem(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<ArmorItem> BISMUTH_BOOTS = ITEMS.registerItem("bismuth_boots",
            properties -> new ArmorItem(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, ArmorType.BOOTS, properties));
}
