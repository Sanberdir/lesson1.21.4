package ru.sanberdir.lesson1_21_4.items;

import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.tags.ModTags;

import java.util.EnumMap;

public class ModArmorMaterials {
    public static ResourceKey<EquipmentAsset> BISMUTH = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "bismuth")
    );

    public static final ArmorMaterial BISMUTH_ARMOR_MATERIAL = new ArmorMaterial(1200,
            Util.make(new EnumMap<>(ArmorType.class), attribute -> {
                attribute.put(ArmorType.BOOTS, 5);
                attribute.put(ArmorType.LEGGINGS, 7);
                attribute.put(ArmorType.CHESTPLATE, 9);
                attribute.put(ArmorType.HELMET, 5);
                attribute.put(ArmorType.BODY, 11);
            }), 16, SoundEvents.ARMOR_EQUIP_DIAMOND,
            2f, 0.1f, ModTags.Items.BISMUTH_REPAIRABLE, BISMUTH);
}