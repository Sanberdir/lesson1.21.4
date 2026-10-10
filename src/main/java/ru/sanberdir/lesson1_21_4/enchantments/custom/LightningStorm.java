package ru.sanberdir.lesson1_21_4.enchantments.custom;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LightningStorm {
    private static final List<ChainInstance> ACTIVE_CHAINS = new ArrayList<>();

    /**
     * Запуск шторма с защитой от дружественного огня.
     *
     * @param level Серверный мир
     * @param caster Тот, кто вызвал молнию (Игрок). Его молния НЕ будет бить.
     * @param target Первая жертва (Моб)
     * @param maxJumps Количество прыжков
     */
    public static void start(ServerLevel level, Entity caster, Entity target, int maxJumps) {
        if (target instanceof LivingEntity livingTarget) {
            ACTIVE_CHAINS.add(new ChainInstance(level, caster, livingTarget, maxJumps));
        }
    }

    public static void tick() {
        Iterator<ChainInstance> iterator = ACTIVE_CHAINS.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().tick()) {
                iterator.remove();
            }
        }
    }

    private static class ChainInstance {
        private final ServerLevel level;
        private final List<LivingEntity> hitEntities = new ArrayList<>();
        private LivingEntity currentTarget;
        private Vec3 lastPosition;
        private int jumpsLeft;
        private int cooldown = 0;

        ChainInstance(ServerLevel level, Entity caster, LivingEntity firstTarget, int maxJumps) {
            this.level = level;
            this.currentTarget = firstTarget;
            this.jumpsLeft = maxJumps;
            this.lastPosition = firstTarget.position().add(0, 4, 0);

            // ВАЖНО: Сразу добавляем создателя молнии в список "уже атакованных".
            // Благодаря этому алгоритм поиска целей (nearby) будет полностью его игнорировать!
            if (caster instanceof LivingEntity livingCaster) {
                this.hitEntities.add(livingCaster);
            }
        }

        boolean tick() {
            if (currentTarget == null || jumpsLeft <= 0) {
                return false;
            }

            if (cooldown-- > 0) return true;
            cooldown = 3;

            Vec3 currentPos = currentTarget.position().add(0, currentTarget.getBbHeight() / 2, 0);

            if (currentTarget.isAlive()) {
                // Создаем кастомный источник урона на базе ванильной молнии, но с кастомным текстовым ключом "chain_lightning"
                DamageSource chainLightningDamage =
                        new DamageSource(
                                level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                                        .getOrThrow(DamageTypes.LIGHTNING_BOLT)
                        ) {
                            @Override
                            public String getMsgId() {
                                return "chain_lightning"; // Наша уникальная метка урона!
                            }
                        };

// Бьем моба именно нашим помеченным источником урона
                currentTarget.hurt(chainLightningDamage, 4.0F);

            }

            hitEntities.add(currentTarget);

            List<Vec3> segment = List.of(this.lastPosition, currentPos);
            sendSegmentToClients(segment);

            level.playSound(null, currentPos.x, currentPos.y, currentPos.z,
                    SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 0.6F, 1.4F + level.random.nextFloat() * 0.3F);

            this.lastPosition = currentPos;

            // Поиск следующей цели
            double range = 7.0;
            AABB box = currentTarget.getBoundingBox().inflate(range);

            // Фильтр автоматически отсекает всех из hitEntities (включая сохраненного игрока-кастера)
            List<LivingEntity> nearby = level.getEntitiesOfClass(LivingEntity.class, box,
                    entity -> entity.isAlive() && !hitEntities.contains(entity));

            LivingEntity nextTarget = null;
            double closestDist = Double.MAX_VALUE;

            for (LivingEntity potential : nearby) {
                double dist = currentTarget.position().distanceToSqr(potential.position());
                if (dist < closestDist) {
                    closestDist = dist;
                    nextTarget = potential;
                }
            }

            currentTarget = nextTarget;
            jumpsLeft--;
            return true;
        }

        private void sendSegmentToClients(List<Vec3> segmentPoints) {
           PacketDistributor.sendToPlayersNear(
                    this.level, null,
                    this.lastPosition.x, this.lastPosition.y, this.lastPosition.z,
                    64.0,
                    new ChainLightningPayload(segmentPoints)
            );
        }
    }
}
