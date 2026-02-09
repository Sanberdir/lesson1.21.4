package ru.sanberdir.lesson1_21_4.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.items.custom.FuelItem;

public class L1214Items {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Lesson1_21_4.MODID);

    public static final DeferredItem<Item> NUGGET_OBSIDIAN = ITEMS.registerItem("nugget_obsidian",
            properties -> new Item(properties),
            new Item.Properties().food(new FoodProperties(12,0.5F,false)));

    public static final DeferredItem<Item> NUGGET_OBSIDIAN2 = ITEMS.registerItem("nugget_obsidian2",
            properties -> new FuelItem(properties,1200),
            new Item.Properties());

    public static final DeferredItem<BlockItem> MY_BETTER_BLOCK = ITEMS.registerItem(
            "my_better_block",
            properties -> new BlockItem(
                    L1214Blocks.MY_BETTER_BLOCK.get(),
                    properties
            ),
            new Item.Properties()
    );
    public static final DeferredItem<BlockItem> MY_BETTER_BLOCK2 = ITEMS.registerItem(
            "my_better_block2",
            properties -> new BlockItem(
                    L1214Blocks.MY_BETTER_BLOCK2.get(),
                    properties
            ),
            new Item.Properties()
    );
    public static final DeferredItem<BlockItem> USUAL_PLANKS = ITEMS.registerItem(
            "usual_planks",
            properties -> new BlockItem(
                    L1214Blocks.USUAL_PLANKS.get(),
                    properties
            ),
            new Item.Properties()
    );

    // Брёвна
    public static final DeferredItem<BlockItem> USUAL_LOG = ITEMS.registerItem(
            "usual_log",
            properties -> new BlockItem(
                    L1214Blocks.USUAL_LOG.get(),
                    properties
            ),
            new Item.Properties()
    );
    public static final DeferredItem<BlockItem> USUAL_WOOD = ITEMS.registerItem(
            "usual_wood",
            properties -> new BlockItem(
                    L1214Blocks.USUAL_WOOD.get(),
                    properties
            ),
            new Item.Properties()
    );
    public static final DeferredItem<BlockItem> STRIPPED_USUAL_LOG = ITEMS.registerItem(
            "stripped_usual_log",
            properties -> new BlockItem(
                    L1214Blocks.STRIPPED_USUAL_LOG.get(),
                    properties
            ),
            new Item.Properties()
    );
    public static final DeferredItem<BlockItem> STRIPPED_USUAL_WOOD = ITEMS.registerItem(
            "stripped_usual_wood",
            properties -> new BlockItem(
                    L1214Blocks.STRIPPED_USUAL_WOOD.get(),
                    properties
            ),
            new Item.Properties()
    );

}
