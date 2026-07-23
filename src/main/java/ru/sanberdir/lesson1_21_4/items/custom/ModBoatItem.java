package ru.sanberdir.lesson1_21_4.items.custom;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import ru.sanberdir.lesson1_21_4.items.entity.ModBoatEntityUsual;
import ru.sanberdir.lesson1_21_4.items.entity.ModChestBoatEntityUsual;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.world.entity.vehicle.AbstractBoat;
// убери импорт Boat если он больше не нужен

public class ModBoatItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final ModBoatEntityUsual.Type type;
    private final boolean hasChest;

    public ModBoatItem(boolean hasChest, ModBoatEntityUsual.Type type, Properties properties) {
        super(properties);
        this.hasChest = hasChest;
        this.type = type;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        }

        Vec3 vec3 = player.getViewVector(1.0F);
        List<Entity> list = level.getEntities(player,
                player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D),
                ENTITY_PREDICATE);

        if (!list.isEmpty()) {
            Vec3 vec31 = player.getEyePosition();
            for (Entity entity : list) {
                AABB aabb = entity.getBoundingBox().inflate((double) entity.getPickRadius());
                if (aabb.contains(vec31)) {
                    return InteractionResult.PASS;
                }
            }
        }

        if (hitresult.getType() == HitResult.Type.BLOCK) {
            AbstractBoat boat = this.getBoat(level, hitresult);  // ← AbstractBoat
            if (boat instanceof ModChestBoatEntityUsual chestBoat) {
                chestBoat.setVariant(this.type);
            } else if (boat instanceof ModBoatEntityUsual modBoat) {
                modBoat.setVariant(this.type);
            }
            boat.setYRot(player.getYRot());

            if (!level.noCollision(boat, boat.getBoundingBox())) {
                return InteractionResult.FAIL;
            }

            if (!level.isClientSide) {
                level.addFreshEntity(boat);
                level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private AbstractBoat getBoat(Level level, HitResult hitResult) {  // ← AbstractBoat
        double x = hitResult.getLocation().x;
        double y = hitResult.getLocation().y;
        double z = hitResult.getLocation().z;

        if (this.hasChest) {
            return new ModChestBoatEntityUsual(level, x, y, z);
        } else {
            return new ModBoatEntityUsual(level, x, y, z);
        }
    }
}