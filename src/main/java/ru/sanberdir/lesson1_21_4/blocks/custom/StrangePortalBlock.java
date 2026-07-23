package ru.sanberdir.lesson1_21_4.blocks.custom;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;
import ru.sanberdir.lesson1_21_4.blocks.L1214Blocks;
import ru.sanberdir.lesson1_21_4.worldgen.dimension.ModDimensions;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public class StrangePortalBlock extends Block implements Portal {
    public static final MapCodec<StrangePortalBlock> CODEC = simpleCodec(StrangePortalBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS;
    private static final Logger LOGGER;
    protected static final int AABB_OFFSET = 2;
    protected static final VoxelShape X_AXIS_AABB;
    protected static final VoxelShape Z_AXIS_AABB;

    public MapCodec<StrangePortalBlock> codec() {
        return CODEC;
    }

    public StrangePortalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(AXIS, Direction.Axis.X));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch ((Direction.Axis)state.getValue(AXIS)) {
            case Z:
                return Z_AXIS_AABB;
            case X:
            default:
                return X_AXIS_AABB;
        }
    }

    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.dimensionType().natural() && level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && random.nextInt(2000) < level.getDifficulty().getId()) {
            while(level.getBlockState(pos).is(this)) {
                pos = pos.below();
            }
        }
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        Direction.Axis axis = direction.getAxis();
        Direction.Axis portalAxis = (Direction.Axis) state.getValue(AXIS);
        boolean unrelatedAxis = portalAxis != axis && axis.isHorizontal();

        if (!unrelatedAxis && !neighborState.is(this) && !isFrameIntact(level, pos, portalAxis)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
    }

    /**
     * Проверяет, что блок портала pos всё ещё находится внутри целой рамки 4x5 (WIDTH x HEIGHT).
     */
    private boolean isFrameIntact(LevelReader level, BlockPos pos, Direction.Axis axis) {
        final int WIDTH = 4;
        final int HEIGHT = 5;

        Direction right = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;

        // находим левый нижний угол ВНУТРЕННЕЙ (2x3) области портальных блоков
        BlockPos bottomLeft = pos;

        while (level.getBlockState(bottomLeft.relative(right.getOpposite())).is(this)) {
            bottomLeft = bottomLeft.relative(right.getOpposite());
        }
        while (level.getBlockState(bottomLeft.below()).is(this)) {
            bottomLeft = bottomLeft.below();
        }

        // угол полной рамки (со смещением -1 по обеим осям, как в createMyPortal/createPortal)
        BlockPos origin = bottomLeft.relative(right, -1).below(1);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                BlockPos check = origin.relative(right, x).above(y);
                boolean frame = x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1;

                if (frame) {
                    if (!level.getBlockState(check).is(Blocks.PRISMARINE)) return false;
                } else {
                    if (!level.getBlockState(check).is(this)) return false;
                }
            }
        }

        return true;
    }

    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.canUsePortal(false)) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    public int getPortalTransitionTime(ServerLevel level, Entity entity) {
        int time;
        if (entity instanceof Player player) {
            time = Math.max(0, level.getGameRules().getInt(player.getAbilities().invulnerable ? GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY : GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY));
        } else {
            time = 0;
        }
        return time;
    }

    @Nullable
    public TeleportTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos pos) {
        ResourceKey<Level> destinationKey = level.dimension() == ModDimensions.CUSTOMDIM_LEVEL_KEY
                ? Level.OVERWORLD
                : ModDimensions.CUSTOMDIM_LEVEL_KEY;
        ServerLevel destinationLevel = level.getServer().getLevel(destinationKey);
        if (destinationLevel == null) {
            return null;
        } else {
            boolean isStrange = destinationLevel.dimension() == ModDimensions.CUSTOMDIM_LEVEL_KEY;
            WorldBorder worldBorder = destinationLevel.getWorldBorder();
            double scale = DimensionType.getTeleportationScale(level.dimensionType(), destinationLevel.dimensionType());

            double targetX = entity.getX() * scale;
            double targetZ = entity.getZ() * scale;
            double targetY = entity.getY();

            if (isStrange) {
                BlockPos surfacePos = findSurfaceSpot(destinationLevel, (int) targetX, (int) targetZ);
                targetX = surfacePos.getX();
                targetY = surfacePos.getY();
                targetZ = surfacePos.getZ();
            }

            BlockPos targetPos = worldBorder.clampToBounds(targetX, targetY, targetZ);
            return this.getExitPortal(destinationLevel, entity, pos, targetPos, isStrange, worldBorder);
        }
    }
    private BlockPos findSurfaceSpot(ServerLevel level, int centerX, int centerZ) {
        for (BlockPos.MutableBlockPos p : BlockPos.spiralAround(
                new BlockPos(centerX, 0, centerZ), 16, Direction.EAST, Direction.SOUTH)) {

            level.getChunk(p.getX() >> 4, p.getZ() >> 4); // гарантируем генерацию

            int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, p.getX(), p.getZ());

            if (hasClearSpace(level, p.getX(), surfaceY, p.getZ())) {
                return new BlockPos(p.getX(), surfaceY, p.getZ());
            }
        }
        // ничего лучше не нашли — берём высоту прямо в центре как запасной вариант
        level.getChunk(centerX >> 4, centerZ >> 4);
        return new BlockPos(centerX, level.getHeight(Heightmap.Types.MOTION_BLOCKING, centerX, centerZ), centerZ);
    }

    private boolean hasClearSpace(ServerLevel level, int x, int y, int z) {
        for (int dy = 0; dy < 6; dy++) { // высота портала + запас
            if (!level.getBlockState(new BlockPos(x, y + dy, z)).isAir()) {
                return false;
            }
        }
        return true;
    }

    private Optional<BlockPos> findClosestStrangePortal(ServerLevel level, BlockPos center, int radius) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        double bestDistance = Double.MAX_VALUE;
        BlockPos best = null;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = level.dimensionType().minY();
                     y < level.dimensionType().minY() + level.dimensionType().height();
                     y++) {

                    pos.set(center.getX() + x, y, center.getZ() + z);

                    if (level.getBlockState(pos).is(L1214Blocks.STRANGE_PORTAL_BLOCK.get())) {
                        double dist = pos.distSqr(center);

                        if (dist < bestDistance) {
                            bestDistance = dist;
                            best = pos.immutable();
                        }
                    }
                }
            }
        }

        return Optional.ofNullable(best);
    }
    @Nullable
    private TeleportTransition getExitPortal(ServerLevel level, Entity entity, BlockPos pos,
                                             BlockPos targetPos, boolean isNether,
                                             WorldBorder worldBorder) {

        Optional<BlockPos> optional =
                findClosestStrangePortal(level, targetPos, 128);

        BlockUtil.FoundRectangle foundRectangle;
        TeleportTransition.PostTeleportTransition postTeleportTransition;

        if (optional.isPresent()) {

            BlockPos portalPos = optional.get();
            BlockState portalState = level.getBlockState(portalPos);

            foundRectangle = BlockUtil.getLargestRectangleAround(
                    portalPos,
                    portalState.getValue(BlockStateProperties.HORIZONTAL_AXIS),
                    21,
                    Direction.Axis.Y,
                    21,
                    p -> level.getBlockState(p).equals(portalState)
            );

            postTeleportTransition =
                    TeleportTransition.PLAY_PORTAL_SOUND.then(
                            e -> e.placePortalTicket(portalPos));

        } else {

            Direction.Axis axis = entity.level()
                    .getBlockState(pos)
                    .getOptionalValue(AXIS)
                    .orElse(Direction.Axis.X);

            foundRectangle = createMyPortal(level, targetPos, axis);

            postTeleportTransition =
                    TeleportTransition.PLAY_PORTAL_SOUND.then(
                            TeleportTransition.PLACE_PORTAL_TICKET);
        }

        return getDimensionTransitionFromExit(
                entity,
                pos,
                foundRectangle,
                level,
                postTeleportTransition
        );
    }
    private static BlockUtil.FoundRectangle createMyPortal(ServerLevel level,
                                                           BlockPos pos,
                                                           Direction.Axis axis) {

        Block frame = Blocks.PRISMARINE;
        Block portal = L1214Blocks.STRANGE_PORTAL_BLOCK.get();

        int width = 4;
        int height = 5;

        Direction right = axis == Direction.Axis.X
                ? Direction.EAST
                : Direction.SOUTH;

        BlockPos bottomLeft = pos;

        // рамка
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                BlockPos p = bottomLeft.relative(right, x).above(y);

                boolean border =
                        x == 0 || x == width - 1 ||
                                y == 0 || y == height - 1;

                if (border) {
                    level.setBlockAndUpdate(p, Blocks.PRISMARINE.defaultBlockState());
                } else {
                    level.setBlockAndUpdate(
                            p,
                            L1214Blocks.STRANGE_PORTAL_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(StrangePortalBlock.AXIS, axis)
                    );
                }
            }
        }

        return new BlockUtil.FoundRectangle(bottomLeft.above(), width - 2, height - 2);
    }
    private static TeleportTransition getDimensionTransitionFromExit(Entity entity, BlockPos pos, BlockUtil.FoundRectangle rectangle, ServerLevel level, TeleportTransition.PostTeleportTransition postTeleportTransition) {
        BlockState state = entity.level().getBlockState(pos);
        Direction.Axis axis;
        Vec3 relativePos;

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            axis = (Direction.Axis)state.getValue(BlockStateProperties.HORIZONTAL_AXIS);
            BlockUtil.FoundRectangle foundRectangle = BlockUtil.getLargestRectangleAround(pos, axis, 21, Direction.Axis.Y, 21, (posToCheck) -> entity.level().getBlockState(posToCheck) == state);
            relativePos = entity.getRelativePortalPosition(axis, foundRectangle);
        } else {
            axis = Direction.Axis.X;
            relativePos = new Vec3(0.5F, 0.0F, 0.0F);
        }

        return createDimensionTransition(level, rectangle, axis, relativePos, entity, postTeleportTransition);
    }

    private static TeleportTransition createDimensionTransition(ServerLevel level, BlockUtil.FoundRectangle rectangle, Direction.Axis axis, Vec3 offset, Entity entity, TeleportTransition.PostTeleportTransition postTeleportTransition) {
        BlockPos cornerPos = rectangle.minCorner;
        BlockState portalState = level.getBlockState(cornerPos);
        Direction.Axis portalAxis = (Direction.Axis)portalState.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
        double width = (double)rectangle.axis1Size;
        double height = (double)rectangle.axis2Size;
        EntityDimensions dimensions = entity.getDimensions(entity.getPose());
        int rotation = axis == portalAxis ? 0 : 90;
        double xOffset = (double)dimensions.width() / 2.0F + (width - (double)dimensions.width()) * offset.x();
        double yOffset = (height - (double)dimensions.height()) * offset.y();
        double zOffset = 0.5F + offset.z();
        boolean isXAxis = portalAxis == Direction.Axis.X;

        Vec3 position = new Vec3(
                (double)cornerPos.getX() + (isXAxis ? xOffset : zOffset),
                (double)cornerPos.getY() + yOffset,
                (double)cornerPos.getZ() + (isXAxis ? zOffset : xOffset)
        );

        Vec3 finalPosition = PortalShape.findCollisionFreePosition(position, level, entity, dimensions);
        return new TeleportTransition(level, finalPosition, Vec3.ZERO, (float)rotation, 0.0F, Relative.union(new Set[]{Relative.DELTA, Relative.ROTATION}), postTeleportTransition);
    }

    public Portal.Transition getLocalTransition() {
        return Transition.CONFUSION;
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        for(int i = 0; i < 4; ++i) {
            double x = (double)pos.getX() + random.nextDouble();
            double y = (double)pos.getY() + random.nextDouble();
            double z = (double)pos.getZ() + random.nextDouble();
            double vx = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double vy = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double vz = ((double)random.nextFloat() - 0.5D) * 0.5D;
            int j = random.nextInt(2) * 2 - 1;

            if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
                x = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                vx = (double)(random.nextFloat() * 2.0F * (float)j);
            } else {
                z = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                vz = (double)(random.nextFloat() * 2.0F * (float)j);
            }

            level.addParticle(ParticleTypes.PORTAL, x, y, z, vx, vy, vz);
        }
    }

    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return ItemStack.EMPTY;
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch ((Direction.Axis)state.getValue(AXIS)) {
                    case Z -> {
                        return (BlockState)state.setValue(AXIS, Direction.Axis.X);
                    }
                    case X -> {
                        return (BlockState)state.setValue(AXIS, Direction.Axis.Z);
                    }
                    default -> {
                        return state;
                    }
                }
            default:
                return state;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AXIS});
    }

    static {
        AXIS = BlockStateProperties.HORIZONTAL_AXIS;
        LOGGER = LogUtils.getLogger();
        X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
        Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    }
}