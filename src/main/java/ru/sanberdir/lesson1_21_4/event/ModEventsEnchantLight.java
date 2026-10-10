package ru.sanberdir.lesson1_21_4.event;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import ru.sanberdir.lesson1_21_4.enchantments.custom.ChainLightningPayload;
import ru.sanberdir.lesson1_21_4.enchantments.custom.ClientLightningRenderer;
import ru.sanberdir.lesson1_21_4.enchantments.custom.LightningStorm;
@EventBusSubscriber(modid = "lesson1_21_4", bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModEventsEnchantLight {

    // 1. Регистрация сетевого пакета (Должно быть на шине MOD)
    @SubscribeEvent
    public static void registerNetwork(RegisterPayloadHandlersEvent event) {
        event.registrar("1.0.0")
                .playToClient(
                        ChainLightningPayload.TYPE,
                        ChainLightningPayload.CODEC,
                        ChainLightningPayload::handleClient
                );
    }

    // 2. Обновление штормов на сервере каждый тик
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        LightningStorm.tick();
    }

    // 3. Уменьшение времени жизни вспышек на клиенте каждый тик
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        ClientLightningRenderer.clientTick();
    }
}