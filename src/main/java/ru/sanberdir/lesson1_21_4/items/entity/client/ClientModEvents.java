package ru.sanberdir.lesson1_21_4.items.entity.client;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import ru.sanberdir.lesson1_21_4.items.entity.ModBoatEntityUsual;
import ru.sanberdir.lesson1_21_4.items.entity.ModEntitiesItem;
import ru.sanberdir.lesson1_21_4.worldgen.wood.ModWoodTypes;

import static ru.sanberdir.lesson1_21_4.Lesson1_21_4.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (ModBoatEntityUsual.Type type : ModBoatEntityUsual.Type.values()) {
            event.registerLayerDefinition(
                    ModUsualBoatRenderer.createBoatModelName(type),
                    BoatModel::createBoatModel
            );
            event.registerLayerDefinition(
                    ModUsualBoatRenderer.createChestBoatModelName(type),
                    BoatModel::createChestBoatModel
            );
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        Sheets.addWoodType(ModWoodTypes.USUAL);
        EntityRenderers.register(ModEntitiesItem.MOD_BOAT_USUAL.get(), pContext -> new ModUsualBoatRenderer(pContext, false));
        EntityRenderers.register(ModEntitiesItem.MOD_CHEST_BOAT_USUAL.get(), pContext -> new ModUsualBoatRenderer(pContext, true));
    }
}
