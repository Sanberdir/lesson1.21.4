package ru.sanberdir.lesson1_21_4.items.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.level.Level;
import ru.sanberdir.lesson1_21_4.items.L1214Items;

public class ModChestBoatEntityUsual extends ChestBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE =
            SynchedEntityData.defineId(ModChestBoatEntityUsual.class, EntityDataSerializers.INT);

    public ModChestBoatEntityUsual(EntityType<? extends ChestBoat> pEntityType, Level pLevel) {
        // В 1.21.4 ChestBoat тоже требует Supplier<Item>
        super(pEntityType, pLevel, () -> L1214Items.USUAL_CHEST_BOAT.get());
    }

    public ModChestBoatEntityUsual(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntitiesItem.MOD_CHEST_BOAT_USUAL.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    // getDropItem() теперь final в AbstractBoat — дроп задаётся через Supplier в конструкторе

    public void setVariant(ModBoatEntityUsual.Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    public ModBoatEntityUsual.Type getModVariant() {
        return ModBoatEntityUsual.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, ModBoatEntityUsual.Type.USUAL.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("Type", 8)) {
            this.setVariant(ModBoatEntityUsual.Type.byName(pCompound.getString("Type")));
        }
    }
}