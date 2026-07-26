package ru.sanberdir.lesson1_21_4.enchantments.custom;

// Импорт для серверного мира
import net.minecraft.server.level.ServerLevel;
// Импорт базового класса сущности
import net.minecraft.world.entity.Entity;
// Импорт причины создания сущности (для валидации)
import net.minecraft.world.entity.EntitySpawnReason;
// Импорт типа сущности (для создания молнии)
import net.minecraft.world.entity.EntityType;
// Импорт класса молнии
import net.minecraft.world.entity.LightningBolt;
// Импорт для работы с векторами (позиция в 3D пространстве)
import net.minecraft.world.phys.Vec3;

// Импорты для работы со списками и итераторами
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс для управления "штормом" из молний.
 * Реализует паттерн: создаёт и управляет цепочкой ударов молний вокруг цели.
 *
 * Работает асинхронно: молнии появляются не мгновенно, а с задержкой в 2 тика,
 * создавая эффект настоящей грозы.
 */
public class LightningStorm {

    /**
     * Глобальный список всех активных штормов.
     * static - потому что штормы должны существовать на протяжении всей игры,
     * независимо от экземпляров класса.
     */
    private static final List<StormData> STORMS = new ArrayList<>();

    /**
     * Статический метод для запуска нового шторма.
     * Создаёт объект StormData и добавляет его в глобальный список.
     *
     * @param level   Серверный мир, где происходит шторм
     * @param center  Центральная сущность, вокруг которой падают молнии
     * @param strikes Количество ударов молний в этом шторме
     */
    public static void start(ServerLevel level, Entity center, int strikes) {
        STORMS.add(new StormData(level, center, strikes));
    }

    /**
     * Основной тик-метод, который должен вызываться каждый игровой тик.
     * Обновляет состояние всех активных штормов и удаляет завершённые.
     *
     * Обычно вызывается из хук-метода или события, например, в ServerTickEvent.
     */
    public static void tick() {
        // Используем Iterator для безопасного удаления элементов во время итерации
        Iterator<StormData> iterator = STORMS.iterator();

        while (iterator.hasNext()) {
            StormData storm = iterator.next();

            // Если шторм завершился (tick() вернул false) - удаляем его из списка
            if (!storm.tick()) {
                iterator.remove();
            }
        }
    }

    /**
     * Внутренний класс, хранящий данные об одном активном шторме.
     * Инкапсулирует состояние и логику обновления.
     */
    private static class StormData {

        /** Мир, в котором происходит шторм */
        private final ServerLevel level;

        /** Центральная сущность (цель) */
        private final Entity center;

        /** Сколько ударов молний осталось нанести */
        private int left;

        /**
         * Таймер задержки между ударами (в тиках).
         * При 0 - можно наносить следующий удар.
         */
        private int cooldown = 0;

        /**
         * Конструктор шторма.
         *
         * @param level   Мир
         * @param center  Центральная сущность
         * @param strikes Общее количество ударов
         */
        StormData(ServerLevel level, Entity center, int strikes) {
            this.level = level;
            this.center = center;
            this.left = strikes;
        }

        /**
         * Обновляет состояние шторма на один игровой тик.
         *
         * @return true - шторм ещё активен, false - шторм завершён
         */
        boolean tick() {

            // Проверяем, жива ли центральная сущность
            // Если цель умерла - шторм немедленно прекращается (безопасность)
            if (!center.isAlive())
                return false;

            // Уменьшаем таймер задержки. Если он > 0 - пропускаем этот тик
            if (cooldown-- > 0)
                return true;

            // Сбрасываем задержку на 2 тика (примерно 0.1 секунды при 20 TPS)
            cooldown = 2;

            // === Генерация позиции для следующей молнии ===

            // Случайный угол (от 0 до 2π радиан) - определяет направление
            double angle = level.random.nextDouble() * Math.PI * 2;

            // Случайное расстояние от центра (от 2 до 7 блоков)
            // Минимум 2 блока - чтобы молния не попала прямо в цель
            double radius = 2 + level.random.nextDouble() * 5;

            // Вычисляем позицию с использованием тригонометрии
            // X и Z меняются, Y остаётся на уровне центра (высота 0 - земля)
            Vec3 pos = center.position().add(
                    Math.cos(angle) * radius,  // Координата X
                    0,                          // Координата Y (высота)
                    Math.sin(angle) * radius   // Координата Z
            );

            // === Создание и размещение молнии ===

            // Создаём сущность молнии с причиной TRIGGERED (вызвана игроком/зачарованием)
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.TRIGGERED);

            if (bolt != null) {
                // Перемещаем молнию на вычисленную позицию
                bolt.moveTo(pos.x, pos.y, pos.z);
                // Добавляем сущность в мир
                level.addFreshEntity(bolt);
            }

            // Уменьшаем счётчик оставшихся ударов
            left--;

            // Возвращаем true, если есть ещё удары, иначе false (шторм завершён)
            return left > 0;
        }
    }
}