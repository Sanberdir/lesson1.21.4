package ru.sanberdir.lesson1_21_4.event;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;

@EventBusSubscriber(modid = Lesson1_21_4.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EventRenderTypes {

    @SubscribeEvent
    public static void onRenderTypes(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(L1214Blocks.GREEN_WHEAT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(L1214Blocks.USUAL_SAPLING.get(), RenderType.cutout());
        // другие блоки с прозрачностью
    }

}
