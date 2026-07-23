package ru.sanberdir.lesson1_21_4.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import ru.sanberdir.lesson1_21_4.Lesson1_21_4;
import ru.sanberdir.lesson1_21_4.worldgen.ModConfiguredFeatures;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower USUAL = new TreeGrower(Lesson1_21_4.MODID + ":usual",
            Optional.empty(), Optional.of(ModConfiguredFeatures.USUAL_KEY), Optional.empty());

}