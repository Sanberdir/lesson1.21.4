package ru.sanberdir.lesson1_21_4.enchantments.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientLightningRenderer {

    // Хранилище активных сегментов молнии
    public static final List<ClientSegment> ACTIVE_SEGMENTS = new ArrayList<>();

    public static void addChain(List<Vec3> points) {
        if (Minecraft.getInstance().level == null || points.size() < 2) return;

        long seed = Minecraft.getInstance().level.random.nextLong();
        // Берем первые две точки (старт и конец одного прыжка)
        ACTIVE_SEGMENTS.add(new ClientSegment(points.get(0), points.get(1), seed, 6)); // Каждый прыжок виден 0.3 сек
    }

    public static void clientTick() {
        Iterator<ClientSegment> iterator = ACTIVE_SEGMENTS.iterator();
        while (iterator.hasNext()) {
            ClientSegment segment = iterator.next();
            segment.ticksLeft--;
            if (segment.ticksLeft <= 0) {
                iterator.remove();
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        if (ACTIVE_SEGMENTS.isEmpty()) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

        VertexConsumer buffer = bufferSource.getBuffer(RenderType.lines());

        poseStack.pushPose();
        poseStack.translate(-camera.x, -camera.y, -camera.z);

        // Рисуем все активные на данный момент сегменты
        for (ClientSegment segment : ACTIVE_SEGMENTS) {
            drawLightningZigzags(buffer, poseStack, segment.start, segment.end, segment.seed);
        }

        poseStack.popPose();
        bufferSource.endBatch(RenderType.lines());
    }

    private static void drawLightningZigzags(VertexConsumer buffer, PoseStack matrix, Vec3 start, Vec3 end, long seed) {
        RandomSource random = RandomSource.create(seed);
        Vec3 current = start;
        int segments = 6;

        for (int i = 1; i <= segments; i++) {
            float progress = (float) i / segments;
            Vec3 target = start.lerp(end, progress);

            if (i < segments) {
                double offset = 0.25; // Степень излома молнии
                target = target.add(
                        (random.nextFloat() - 0.5) * offset,
                        (random.nextFloat() - 0.5) * offset,
                        (random.nextFloat() - 0.5) * offset
                );
            }

            var pose = matrix.last().pose();

            // Точка А
            buffer.addVertex(pose, (float)current.x, (float)current.y, (float)current.z)
                    .setColor(0.4F, 0.8F, 1.0F, 1.0F) // Электрический голубой
                    .setNormal(matrix.last(), 0.0F, 1.0F, 0.0F);

            // Точка Б
            buffer.addVertex(pose, (float)target.x, (float)target.y, (float)target.z)
                    .setColor(0.4F, 0.8F, 1.0F, 1.0F)
                    .setNormal(matrix.last(), 0.0F, 1.0F, 0.0F);

            current = target;
        }
    }

    private static class ClientSegment {
        final Vec3 start;
        final Vec3 end;
        final long seed;
        int ticksLeft;

        ClientSegment(Vec3 start, Vec3 end, long seed, int ticksLeft) {
            this.start = start;
            this.end = end;
            this.seed = seed;
            this.ticksLeft = ticksLeft;
        }
    }
}
