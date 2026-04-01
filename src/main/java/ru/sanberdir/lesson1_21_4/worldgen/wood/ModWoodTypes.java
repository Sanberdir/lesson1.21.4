package ru.sanberdir.lesson1_21_4.worldgen.wood;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;

public class ModWoodTypes {
    public static final WoodType USUAL = WoodType.register(new WoodType(Lesson1_21_4.MODID + ":usual", BlockSetType.OAK));
}