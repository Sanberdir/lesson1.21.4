package ru.sanberdir.lesson1_21_4.enchantments;

// Импорт для регистраций (хранилище всех зарегистрированных объектов)
import net.minecraft.core.registries.Registries;
// Импорт контекста для загрузки данных (используется при генерации мира)
import net.minecraft.data.worldgen.BootstrapContext;
// Импорт ResourceKey - уникальный идентификатор объекта в реестре
import net.minecraft.resources.ResourceKey;
// Импорт ResourceLocation - путь к ресурсу (modid:path)
import net.minecraft.resources.ResourceLocation;
// Импорт тегов для зачарований (группы зачарований)
import net.minecraft.tags.EnchantmentTags;
// Импорт тегов для предметов (группы предметов)
import net.minecraft.tags.ItemTags;
// Импорт слота экипировки (главная рука, броня и т.д.)
import net.minecraft.world.entity.EquipmentSlotGroup;
// Импорт класса зачарования
import net.minecraft.world.item.enchantment.Enchantment;
// Импорт компонентов эффектов зачарования
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
// Импорт цели зачарования (кто атакует, кто получает урон)
import net.minecraft.world.item.enchantment.EnchantmentTarget;

// Импорт главного класса мода (для получения MODID)
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
// Импорт пользовательского эффекта зачарования
import ru.sanberdir.lesson1_21_4.enchantments.custom.LightningStrikerEnchantmentEffect;

/**
 * Класс для регистрации пользовательских зачарований мода.
 * Использует систему Data-Driven Enchantments (зачарования через данные),
 * которая появилась в Minecraft 1.20.5 / 1.21.
 *
 * Все зачарования регистрируются через Bootstrap (загрузчик данных),
 * а не через обычный реестр, что позволяет использовать JSON-конфигурации.
 */
public class ModEnchantments {

    /**
     * Уникальный ключ зачарования "Удар молнии".
     * ResourceKey используется для ссылки на объект в реестре зачарований.
     *
     * Формат: modid:lightning_striker
     * Будет использоваться в коде и для генерации JSON файлов.
     */
    public static final ResourceKey<Enchantment> LIGHTNING_STRIKER = ResourceKey.create(
            Registries.ENCHANTMENT,  // Реестр, в котором будет зарегистрировано зачарование
            ResourceLocation.fromNamespaceAndPath(Lesson1_21_4.MODID, "lightning_striker")
    );

    /**
     * Метод загрузки зачарований в игру.
     * Вызывается автоматически при загрузке данных (датапаков).
     *
     * BootstrapContext - контекст, предоставляющий доступ к реестрам
     * и позволяющий регистрировать объекты.
     *
     * @param context Контекст загрузки данных
     */
    public static void bootstrap(BootstrapContext<Enchantment> context) {

        // Получаем доступ к реестру зачарований (нужен для проверки исключений)
        var enchantments = context.lookup(Registries.ENCHANTMENT);

        // Получаем доступ к реестру предметов (нужен для проверки тегов предметов)
        var items = context.lookup(Registries.ITEM);

        // Регистрируем наше зачарование
        register(context, LIGHTNING_STRIKER,

                // Строитель зачарования - создаём конфигурацию
                Enchantment.enchantment(

                                // === ОПРЕДЕЛЕНИЕ ЗАЧАРОВАНИЯ (Enchantment.definition) ===
                                Enchantment.definition(

                                        // На какие предметы можно наложить (тег предметов)
                                        // WEAPON_ENCHANTABLE - все оружие (мечи, топоры, булавы и т.д.)
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),

                                        // На какие предметы можно наложить в виде книги (тег предметов)
                                        // SWORD_ENCHANTABLE - только мечи (для книг)
                                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),

                                        // Максимальный уровень зачарования (можно получить до 5 уровня)
                                        5,

                                        // Минимальный уровень зачарования (стартовый уровень в таблице)
                                        2,

                                        // Первая стоимость (для уровня I) - стоимость опыта в столах
                                        // dynamicCost(5, 7) = 5 + (уровень - 1) * 7
                                        Enchantment.dynamicCost(5, 7),

                                        // Вторая стоимость (для максимального уровня)
                                        // dynamicCost(25, 7) = 25 + (уровень - 1) * 7
                                        Enchantment.dynamicCost(25, 7),

                                        // Вес зачарования (редкость) - 2 (средняя редкость)
                                        // Чем выше вес, тем чаще появляется в столах зачарований
                                        2,

                                        // Слот, в котором должен быть предмет для активации зачарования
                                        // MAINHAND - только в основной руке
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )

                        // === ДОПОЛНИТЕЛЬНЫЕ НАСТРОЙКИ ===

                        // Указываем, что зачарование несовместимо с другими зачарованиями
                        // DAMAGE_EXCLUSIVE - все зачарования урона (Острота, Бич членистоногих,
                        // Небесная кара и т.д.) - они не могут быть на одном предмете
                        .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))

                        // === ДОБАВЛЯЕМ ЭФФЕКТ ЗАЧАРОВАНИЯ ===
                        // withEffect - добавляет эффект, который срабатывает при определённом событии
                        .withEffect(
                                // Компонент эффекта - POST_ATTACK (после атаки)
                                EnchantmentEffectComponents.POST_ATTACK,

                                // Цель зачарования - ATTACKER (кто атакует)
                                EnchantmentTarget.ATTACKER,

                                // Цель зачарования - VICTIM (кого атакуют)
                                EnchantmentTarget.VICTIM,

                                // Экземпляр эффекта (наша логика)
                                new LightningStrikerEnchantmentEffect()
                        )
        );
    }

    /**
     * Вспомогательный метод для регистрации зачарования.
     * Упрощает код и обеспечивает единообразие.
     *
     * @param registry Контекст загрузки данных
     * @param key      Уникальный ключ зачарования
     * @param builder  Строитель с конфигурацией зачарования
     */
    private static void register(BootstrapContext<Enchantment> registry,
                                 ResourceKey<Enchantment> key,
                                 Enchantment.Builder builder) {
        // Строим зачарование и регистрируем его в контексте
        // key.location() - преобразует ResourceKey в ResourceLocation (путь к файлу)
        registry.register(key, builder.build(key.location()));
    }
}