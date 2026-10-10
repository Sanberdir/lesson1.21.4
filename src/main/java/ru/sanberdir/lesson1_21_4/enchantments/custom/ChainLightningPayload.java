package ru.sanberdir.lesson1_21_4.enchantments.custom;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public record ChainLightningPayload(List<Vec3> points) implements CustomPacketPayload {

    public static final Type<ChainLightningPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("lesson1_21_4", "chain_lightning"));

    // Кодек для чтения и записи списка координат в буфер сети
    public static final StreamCodec<FriendlyByteBuf, ChainLightningPayload> CODEC = CustomPacketPayload.codec(
            ChainLightningPayload::write, ChainLightningPayload::new
    );

    private ChainLightningPayload(FriendlyByteBuf buf) {
        this(readPoints(buf));
    }

    private static List<Vec3> readPoints(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<Vec3> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble()));
        }
        return list;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(points.size());
        for (Vec3 p : points) {
            buf.writeDouble(p.x);
            buf.writeDouble(p.y);
            buf.writeDouble(p.z);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // Обработчик пакета на стороне клиента
    public static void handleClient(ChainLightningPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            // ДЕБАГ-ЛОГ: Проверяем, дошёл ли пакет до клиента
            System.out.println("ПАКЕТ ПОЛУЧЕН НА КЛИЕНТЕ! ТочеПАКЕТ ПОЛУЧЕН НА КЛИЕНТЕ! Точек: " + payload.points().size());
            ClientLightningRenderer.addChain(payload.points());
        });
    }
}
