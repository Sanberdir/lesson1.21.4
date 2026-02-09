package ru.sanberdir.lesson1_21_4.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;

public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {
    public ModFlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }

    /**
     * МЕТОД ВОСПЛАМЕНЯЕМОСТИ (isFlammable)
     *
     * Определяет, может ли блок загореться от огня/лавы.
     * В Minecraft огонь распространяется только на блоки, возвращающие true.
     *
     * Параметры:
     * @param state - текущее состояние блока
     * @param level - мир/уровень, где находится блок
     * @param pos - позиция блока в мире (координаты x, y, z)
     * @param direction - направление, С КОТОРОГО приходит огонь
     *                 (например, Direction.UP если огонь снизу)
     *
     * Возвращаемое значение: true - блок может загореться с этой стороны
     *
     * Важно: Возврат true НЕ означает, что блок сгорит сразу,
     * это лишь разрешает огню "прицепиться" к блоку.
     */
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true; // Блок может загореться с любой стороны
    }

    /**
     * ВОСПЛАМЕНЯЕМОСТЬ (getFlammability)
     *
     * Определяет ШАНС того, что блок загорится, когда к нему поднесут огонь.
     * Чем выше значение - тем легче/быстрее блок загорается.
     *
     * Значения в Minecraft (оригинальные примеры):
     * - Деревянные блоки: 5 (легко горят)
     * - Шерсть: 30 (очень легко горит)
     * - Листья: 60 (чрезвычайно легко горят)
     * - Камень/земля: 0 (не горят)
     *
     * Механика: При проверке горения игра генерирует случайное число.
     * Если random < flammability/300 (где flammability - это возвращаемое значение),
     * то блок загорается.
     *
     * Пример с flammability = 5:
     * Вероятность загореться = 5/300 = 1.67% за тик
     *
     * @return Значение от 0 до 300 (рекомендуется использовать значения из ванильных блоков)
     */
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5; // Стандартное значение для дерева (как у досок и брёвен)
    }

    /**
     * СКОРОСТЬ РАСПРОСТРАНЕНИЯ ОГНЯ (getFireSpreadSpeed)
     *
     * Определяет, как БЫСТРО огонь будет распространяться ОТ этого блока
     * к соседним блокам.
     *
     * Значения в Minecraft:
     * - Деревянные блоки: 5
     * - Угольные блоки: 5
     * - Шерсть: 30 (очень быстро распространяет огонь)
     * - Книжные полки: 30
     * - Листья: 60 (чрезвычайно быстро)
     *
     * Механика: Когда блок горит, он пытается поджечь соседние блоки.
     * Чем выше это значение - тем выше шанс, что соседний блок загорится
     * в следующем тике.
     *
     * Формула распространения: spreadSpeed * (100 + 40) / 300
     * Для spreadSpeed = 5: 5 * 140 / 300 ≈ 2.33% шанс за тик
     *
     * @return Значение от 0 до 100 (обычно 0-60 для ванильных блоков)
     */
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5; // Стандартное значение для дерева
    }

    /**
     * МОДИФИКАЦИЯ БЛОКА ИНСТРУМЕНТОМ (getToolModifiedState)
     *
     * Этот метод позволяет блоку менять своё состояние при использовании
     * определённого инструмента (топора в данном случае).
     *
     * Механика строгания (stripping) брёвен в Minecraft:
     * 1. Игрок использует топор (AxeItem) на блоке
     * 2. Игра проверяет, поддерживает ли блок строгание
     * 3. Если да - возвращается новое состояние блока (строганое бревно)
     * 4. Игра заменяет блок и проигрывает звук/анимацию
     *
     * Параметры:
     * @param state - текущее состояние блока (нестроганое бревно)
     * @param context - контекст использования (информация об игроке, позиции и т.д.)
     * @param itemAbility - способность инструмента (в данном случае - строгание)
     * @param simulate - если true, только проверка (без реального изменения)
     *
     * @return Новое состояние блока или null, если модификация невозможна
     */
    @Override
    public @org.jetbrains.annotations.Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        // Проверяем, что игрок использует топор
        if(context.getItemInHand().getItem() instanceof AxeItem) {

            // Проверяем, является ли текущий блок обычным бревном
            if(state.is(L1214Blocks.USUAL_LOG.get())) {
                // Возвращаем строганое бревно с сохранением оси ориентации
                return L1214Blocks.STRIPPED_USUAL_LOG.get()
                        .defaultBlockState()          // Базовое состояние строганого бревна
                        .setValue(AXIS, state.getValue(AXIS)); // Сохраняем ось (вертикально/горизонтально)
            }

            // Проверяем, является ли текущий блок древесиной (кора со всех сторон)
            if(state.is(L1214Blocks.USUAL_WOOD.get())) {
                // Возвращаем строганую древесину с сохранением оси
                return L1214Blocks.STRIPPED_USUAL_WOOD.get()
                        .defaultBlockState()
                        .setValue(AXIS, state.getValue(AXIS));
            }
        }

        // Если не топор или не наш блок - стандартная обработка
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}