package ru.sanberdir.lesson1_21_4.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.enchantments.custom.LightningStorm;

@EventBusSubscriber(modid = Lesson1_21_4.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        LightningStorm.tick();
    }
}