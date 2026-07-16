package ru.sanberdir.lesson1_21_4;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.blocks.custom.ModFlammableBlocks;
import ru.sanberdir.lesson1_21_4.blocks.entity.ModBlockEntities;
import ru.sanberdir.lesson1_21_4.effect.ModEffects;
import ru.sanberdir.lesson1_21_4.entity.ModEntities;
import ru.sanberdir.lesson1_21_4.entity.client.GeckoRenderer;
import ru.sanberdir.lesson1_21_4.items.L1214Items;
import ru.sanberdir.lesson1_21_4.items.entity.ModEntitiesItem;
import ru.sanberdir.lesson1_21_4.items.entity.client.ModUsualBoatRenderer;
import ru.sanberdir.lesson1_21_4.potions.ModPotions;
import ru.sanberdir.lesson1_21_4.sounds.ModSounds;
import ru.sanberdir.lesson1_21_4.tab.L1214Tabs;
import ru.sanberdir.lesson1_21_4.villager.ModVillagers;
import ru.sanberdir.lesson1_21_4.worldgen.biome.ModRegion;
import ru.sanberdir.lesson1_21_4.worldgen.biome.ModSurfaceRules;
import ru.sanberdir.lesson1_21_4.worldgen.wood.ModWoodTypes;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(Lesson1_21_4.MODID)
public class Lesson1_21_4 {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "lesson1_21_4";

    public Lesson1_21_4(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        L1214Items.ITEMS.register(modEventBus);
        L1214Blocks.BLOCKS.register(modEventBus);
        L1214Tabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        Regions.register(new ModRegion());
        ModPotions.POTIONS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        ModSounds.SOUND_EVENTS.register(modEventBus);
        ModVillagers.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModEntitiesItem.ITEM_ENTITIES.register(modEventBus);  // ← добавь эту строку
        modEventBus.addListener((EntityRenderersEvent.RegisterRenderers event) -> {
            event.registerBlockEntityRenderer(ModBlockEntities.USUAL_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.USUAL_HANGING_SIGN.get(), HangingSignRenderer::new);
        });

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            ModFlammableBlocks.registerFlammableBlocks();

            SurfaceRuleManager.addSurfaceRules(
                    SurfaceRuleManager.RuleCategory.OVERWORLD,
                    MODID,
                    ModSurfaceRules.makeRules()
            );

        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.GECKO.get(), GeckoRenderer::new);
            Sheets.addWoodType(ModWoodTypes.USUAL);
            EntityRenderers.register(ModEntitiesItem.MOD_BOAT_USUAL.get(), pContext -> new ModUsualBoatRenderer(pContext, false));
            EntityRenderers.register(ModEntitiesItem.MOD_CHEST_BOAT_USUAL.get(), pContext -> new ModUsualBoatRenderer(pContext, true));

        }
    }
}
