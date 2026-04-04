package ru.sanberdir.lesson1_21_4.items.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;

public class ModModelLayersItem {

    public static final ModelLayerLocation USUAL_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "boat/usual"), "main");

    public static final ModelLayerLocation USUAL_CHEST_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "chest_boat/usual"), "main");
}