package ru.sanberdir.lesson1_21_4.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> INCORRECT_FOR_BISMUTH_TOOL = createTag("incorrect_for_bismuth_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> BISMUTH_REPAIRABLE = createTag("bismuth_repairable");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, name));
        }
    }
}