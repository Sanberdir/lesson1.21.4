package ru.sanberdir.lesson1_21_4.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.items.L1214Items;

import java.util.List;

@EventBusSubscriber(modid = Lesson1_21_4.MODID)
public class AddTradersVillagers {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {

        // Добавление торгов для ФЕРМЕРА
        if (event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Уровень 1: 2 изумруда -> 12 Raw Bismuth
            trades.get(1).add((trader, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(L1214Items.RAW_BISMUTH.get(), 12),
                    15, 8, 0.02f));

            // Уровень 2: 5 изумрудов -> 6 Raw Bismuth
            trades.get(2).add((trader, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(L1214Items.RAW_BISMUTH.get(), 6),
                    5, 9, 0.035f));

            // Уровень 3: 8 золотых слитков -> 2 изумруда
            trades.get(3).add((trader, random) -> new MerchantOffer(
                    new ItemCost(Items.GOLD_INGOT, 8),
                    new ItemStack(Items.EMERALD, 2),
                    2, 12, 0.075f));
        }

        // Добавление торгов для БИБЛИОТЕКАРЯ
        if (event.getType() == VillagerProfession.LIBRARIAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Уровень 1: 32 изумруда -> Книга «Шипы II»
            trades.get(1).add((pTrader, pRandom) -> {
                // В 1.21.4 EnchantedBookItem удалён — используем DataComponents
                Holder<Enchantment> thorns = pTrader.level()
                        .registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(Enchantments.THORNS);

                ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
                ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
                mutable.set(thorns, 2);
                enchantedBook.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());

                return new MerchantOffer(
                        new ItemCost(Items.EMERALD, 32),
                        enchantedBook,
                        2, 8, 0.02f);
            });
        }
    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        // Обычная сделка: 12 изумрудов -> 1 Green Wheat
        genericTrades.add((trader, random) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 12),
                new ItemStack(L1214Items.GREEN_WHEAT.get(), 1),
                3, 2, 0.2f));

        // Редкая сделка: 24 изумруда -> 1 Usual Boat
        rareTrades.add((trader, random) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 24),
                new ItemStack(L1214Items.USUAL_BOAT.get(), 1),
                2, 12, 0.15f));
    }
}