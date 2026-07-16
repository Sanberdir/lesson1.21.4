package ru.sanberdir.lesson1_21_4.worldgen.biome;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModSurfaceRules {

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.TURQUOISE_VALLEY),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 2),
                                SurfaceRules.state(Blocks.SAND.defaultBlockState())
                        ),
                        SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
                )
        );
    }
}