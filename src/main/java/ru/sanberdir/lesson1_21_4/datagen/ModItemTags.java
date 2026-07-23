package ru.sanberdir.lesson1_21_4.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;

public class ModItemTags {
    public static final TagKey<Item> USUAL_LOGS = create("usual_logs");
    public static final TagKey<Item> CHARCOAL_RECIPE =
            TagKey.create(Registries.ITEM, ResourceLocation.tryParse(Lesson1_21_4.MODID + ":charcoal_recipe"));
    private ModItemTags() {
    }

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(name));
    }

    public static TagKey<Item> create(ResourceLocation name) {
        return TagKey.create(Registries.ITEM, name);
    }
}
