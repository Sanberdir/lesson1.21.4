package ru.sanberdir.lesson1_21_4.blocks.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;

import java.lang.reflect.Method;

public class ModFlammableBlocks {


    public static void registerFlammableBlocks() {
        try {
            // Получаем метод setFlammable из класса FireBlock через рефлексию
            Method setFlammableMethod = FireBlock.class.getDeclaredMethod("setFlammable", Block.class, int.class, int.class);
            // Делаем метод доступным для вызова
            setFlammableMethod.setAccessible(true);
            // Получаем экземпляр блока огня (FireBlock)
            FireBlock fireBlock = (FireBlock) Blocks.FIRE;
            // Устанавливаем горючие свойства для пользовательского блока USUAL_LOG:

            // - 5: вероятность воспламенения (Ignite Odds). Низкая вероятность для древесины.
            //       0: блок никогда не загорается (например, камень).
            //       60: блок очень легко воспламеняется (например, лиственница или шерсть).

            // - 20: скорость распространения огня (Burn Odds). Средняя скорость для древесных блоков.
            //       0: огонь не распространяется через блок (например, камень).
            //       100: огонь распространяется очень быстро (например, листья).
            setFlammableMethod.invoke(fireBlock, L1214Blocks.USUAL_PLANKS.get(), 15, 40);
        } catch (Exception e) {
            // Если происходит ошибка (например, метод не найден), она будет выведена в консоль
            e.printStackTrace();
        }
    }
}
