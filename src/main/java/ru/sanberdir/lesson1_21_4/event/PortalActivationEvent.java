package ru.sanberdir.lesson1_21_4.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.blocks.custom.StrangePortalBlock;

public class PortalActivationEvent {

    private static final int WIDTH = 4;   // включая рамку
    private static final int HEIGHT = 5;  // включая рамку

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {

        Player player = event.getEntity();
        Level level = event.getLevel();

        if (level.isClientSide) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!player.getItemInHand(event.getHand()).is(Items.PRISMARINE_SHARD)) return;

        BlockPos clicked = event.getPos();
        if (!level.getBlockState(clicked).is(Blocks.PRISMARINE)) return;

        // пробуем обе ориентации рамки: вдоль X и вдоль Z
        for (Direction.Axis axis : new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}) {

            BlockPos origin = findPortalOrigin(level, clicked, axis);

            if (origin != null) {
                createPortal(level, origin, axis);

                level.playSound(null, clicked, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1F, 1F);

                if (!player.getAbilities().instabuild)
                    player.getItemInHand(event.getHand()).shrink(1);

                event.setCancellationResult(InteractionResult.CONSUME);
                event.setCanceled(true);
                return;
            }
        }
    }

    /**
     * direction — направление "вдоль ширины" рамки (Direction.EAST для оси X, Direction.SOUTH для оси Z)
     */
    private static BlockPos findPortalOrigin(Level level, BlockPos clicked, Direction.Axis axis) {
        Direction dir = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;

        for (int py = 0; py < HEIGHT; py++) {
            for (int px = 0; px < WIDTH; px++) {

                BlockPos origin = clicked.relative(dir, -px).below(py);

                if (isValidFrame(level, origin, dir)) {
                    return origin;
                }
            }
        }
        return null;
    }

    private static boolean isValidFrame(Level level, BlockPos origin, Direction dir) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                BlockPos check = origin.relative(dir, x).above(y);
                boolean frame = x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1;

                if (frame) {
                    if (!level.getBlockState(check).is(Blocks.PRISMARINE)) return false;
                } else {
                    if (!level.getBlockState(check).isAir()) return false;
                }
            }
        }
        return true;
    }

    private static void createPortal(Level level, BlockPos origin, Direction.Axis axis) {
        Direction dir = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;

        for (int y = 1; y < HEIGHT - 1; y++) {
            for (int x = 1; x < WIDTH - 1; x++) {
                BlockPos p = origin.relative(dir, x).above(y);
                level.setBlock(
                        p,
                        L1214Blocks.STRANGE_PORTAL_BLOCK.get()
                                .defaultBlockState()
                                .setValue(StrangePortalBlock.AXIS, axis),
                        3
                );
            }
        }
    }
}