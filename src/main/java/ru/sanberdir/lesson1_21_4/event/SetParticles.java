package ru.sanberdir.lesson1_21_4.event;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.particle.ModParticles;

// Автоматически регистрирует этот класс на шине событий NeoForge
@EventBusSubscriber(modid = "lesson1_21_4") // Замените на ID вашего мода
public class SetParticles {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();

        // Проверяем, что код на сервере и игрок движется
        if (!level.isClientSide() && (player.getDeltaMovement().x != 0 || player.getDeltaMovement().z != 0)) {

            // ПЕРИОДИЧНОСТЬ: Проверяем тики игрока.
            // Число 5 означает, что эффект сработает только каждый 5-й тик.
            // Чем больше число, тем реже будут появляться частицы.
            if (player.tickCount % 5 == 0) {

                BlockPos posUnderPlayer = player.getOnPos();

                if (level.getBlockState(posUnderPlayer).is(L1214Blocks.BISMUTH_ORE)) {
                    ServerLevel serverLevel = (ServerLevel) level;

                    double particleX = posUnderPlayer.getX() + 0.5;
                    double particleY = posUnderPlayer.getY() + 1.05;
                    double particleZ = posUnderPlayer.getZ() + 0.5;

                    serverLevel.sendParticles(
                            ModParticles.BISMUTH_PARTICLES.get(),
                            particleX, particleY, particleZ,
                            3,               // Немного увеличили количество до 3 для видимости
                            0.2, 0.0, 0.2,
                            0.05             // Добавили крошечную скорость, чтобы брызги слегка подлетали
                    );
                }
            }
        }
    }
}