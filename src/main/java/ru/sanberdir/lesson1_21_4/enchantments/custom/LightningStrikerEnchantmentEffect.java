package ru.sanberdir.lesson1_21_4.enchantments.custom;

// Импорт для кодека (сериализации/десериализации)
import com.mojang.serialization.MapCodec;
// Импорт для серверного мира (заклинания выполняются только на сервере)
import net.minecraft.server.level.ServerLevel;
// Импорт для генерации случайных чисел
import net.minecraft.util.RandomSource;
// Импорт для сущности (моб, игрок и т.д.)
import net.minecraft.world.entity.Entity;
// Импорт для предмета с зачарованием, который используется в данный момент
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
// Импорт базового интерфейса для эффектов зачарований, воздействующих на сущности
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
// Импорт для позиции в пространстве (вектор)
import net.minecraft.world.phys.Vec3;

/**
 * Эффект зачарования "Удар молнии".
 * При активации вызывает несколько ударов молний вокруг цели.
 *
 * Это record (неизменяемый класс), реализующий интерфейс EnchantmentEntityEffect.
 * Record выбран потому, что эффект не хранит состояние - только логику применения.
 */
public record LightningStrikerEnchantmentEffect() implements EnchantmentEntityEffect {

    /**
     * Кодек для регистрации эффекта в системе зачарований Minecraft.
     * MapCodec.unit() создаёт кодек, который всегда возвращает один и тот же экземпляр,
     * так как у эффекта нет параметров конфигурации.
     */
    public static final MapCodec<LightningStrikerEnchantmentEffect> CODEC =
            MapCodec.unit(LightningStrikerEnchantmentEffect::new);

    /**
     * Основной метод применения эффекта зачарования.
     * Вызывается игровым движком при срабатывании зачарования.
     *
     * @param level            Серверный уровень (мир), где происходит действие
     * @param enchantmentLevel Уровень зачарования (I, II, III и т.д.)
     * @param item             Информация о предмете с зачарованием, который используется
     * @param entity           Сущность, на которую направлен эффект (цель)
     * @param pos              Позиция, где происходит эффект (обычно центр цели)
     */
    @Override
    public void apply(ServerLevel level, int enchantmentLevel,
                      EnchantedItemInUse item, Entity entity, Vec3 pos) {

        // Получаем генератор случайных чисел для текущего мира
        RandomSource random = level.getRandom();

        // Определяем количество ударов молний на основе уровня зачарования
        int strikes = switch (enchantmentLevel) {
            // I уровень: от 1 до 4 ударов (включительно)
            case 1 -> random.nextIntBetweenInclusive(1, 4);
            // II уровень: от 4 до 16 ударов (включительно)
            case 2 -> random.nextIntBetweenInclusive(4, 16);
            // Другие уровни (0 или выше 2) - молнии не вызываются
            default -> 0;
        };

        // Запускаем процесс создания грозы с указанным количеством ударов
        // Статический метод LightningStorm.start создаёт и управляет цепочкой молний
        LightningStorm.start(level, entity, strikes);
    }

    /**
     * Возвращает кодек для сериализации/десериализации этого эффекта.
     * Используется системой регестрации зачарований.
     *
     * @return Кодек, ассоциированный с этим эффектом
     */
    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}