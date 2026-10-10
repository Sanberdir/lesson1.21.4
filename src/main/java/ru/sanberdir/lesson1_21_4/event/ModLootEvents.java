package ru.sanberdir.lesson1_21_4.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import java.util.Optional;

@EventBusSubscriber(modid = "lesson1_21_4")
public class ModLootEvents {

    /**
     * Перехватывает выпадающие предметы в момент смерти любого существа.
     * Мясо пережаривается СТРОГО если моб погиб от нашей цепной молнии.
     */
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        if (event.getEntity().level() instanceof ServerLevel serverLevel) {

            // ИСПРАВЛЕНИЕ: Проверяем кастомный MSG ID источника урона, который мы указали в LightningStorm
            if ("chain_lightning".equals(event.getSource().getMsgId())) {
                LivingEntity entity = event.getEntity();
                RecipeManager recipeManager = serverLevel.recipeAccess();

                event.getDrops().forEach(itemEntity -> {
                    ItemStack originalStack = itemEntity.getItem();
                    SingleRecipeInput input = new SingleRecipeInput(originalStack.copyWithCount(1));

                    // Ищем рецепт в печке
                    Optional<RecipeHolder<SmeltingRecipe>> smeltingRecipe = recipeManager
                            .getRecipeFor(RecipeType.SMELTING, input, serverLevel);

                    if (smeltingRecipe.isPresent()) {
                        ItemStack cookedResult = smeltingRecipe.get().value().assemble(input, serverLevel.registryAccess());

                        if (!cookedResult.isEmpty()) {
                            ItemStack newStack = cookedResult.copyWithCount(originalStack.getCount());
                            itemEntity.setItem(newStack);
                        }
                    }
                });
            }
        }
    }
}
