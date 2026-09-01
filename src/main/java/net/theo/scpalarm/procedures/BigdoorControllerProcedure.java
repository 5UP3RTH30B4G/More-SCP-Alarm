package net.theo.scpalarm.procedures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.theo.scpalarm.block.NewdoorBlock;
import net.theo.scpalarm.block.NewdoorPartBlock;
import net.theo.scpalarm.init.MoreScpAlarmModBlocks;
import net.theo.scpalarm.init.MoreScpAlarmModSounds;
import net.theo.scpalarm.MoreScpAlarmMod;
import net.theo.scpalarm.network.BigdoorAnimationMessage;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.PacketDistributor;

public final class BigdoorControllerProcedure {
    private static final int MAX_SLIDE = 2;
    private static final int TICKS_PER_STEP = 30;
    private static final List<DoorController> DOORS = new ArrayList<>();
    private static boolean internalChange;

    private BigdoorControllerProcedure() {
    }

    public static void neighborChanged(ServerLevel level, BlockPos pos) {
        DoorController door = findOrCreate(level, pos);
        if (door == null) {
            return;
        }

        boolean powered = level.getBestNeighborSignal(pos) > 0;
        if (powered && !door.powered) {
            door.powered = true;
            door.targetSlide = door.slide == 0 ? MAX_SLIDE : 0;
            door.ticksUntilStep = TICKS_PER_STEP;
            level.playSound(null, door.base, door.targetSlide > door.slide
                    ? MoreScpAlarmModSounds.DOOR_BIGDOOR_OPEN.get()
                    : MoreScpAlarmModSounds.DOOR_BIGDOOR_CLOSE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
               door.sendAnimationPacket();
               door.markAnimatingImmediately();
        } else if (!powered) {
            door.powered = false;
        }
    }

    public static void tick(MinecraftServer server) {
        for (ServerLevel level : server.getAllLevels()) {
            Iterator<DoorController> iterator = new ArrayList<>(DOORS).iterator();
            while (iterator.hasNext()) {
                DoorController door = iterator.next();
                if (door.level != level) {
                    continue;
                }
                if (!door.isValid()) {
                    DOORS.remove(door);
                    continue;
                }
                if (door.animationTicksRemaining > 0) {
                    door.animationTicksRemaining--;
                    if (door.animationTicksRemaining == 0) {
                        door.finishAnimation();
                    }
                    continue;
                }
                if (door.slide == door.targetSlide) {
                    continue;
                }
                if (--door.ticksUntilStep > 0) {
                    continue;
                }
                door.ticksUntilStep = TICKS_PER_STEP;
                if (!door.moveOneStep()) {
                    door.targetSlide = door.slide;
                } else if (door.slide == door.targetSlide) {
                    door.finishAnimation();
                }
            }
        }
    }

    public static void onRemoved(ServerLevel level, BlockPos pos, BlockState newState) {
        if (internalChange || newState.getBlock() == MoreScpAlarmModBlocks.NEWDOOR.get()
                || newState.getBlock() == MoreScpAlarmModBlocks.NEWDOOR_PART.get()) {
            return;
        }

        DoorController door = findOrCreate(level, pos);
        if (door == null) {
            return;
        }
        DOORS.remove(door);
        internalChange = true;
        try {
            for (BlockPos doorPos : door.positions()) {
                if (!doorPos.equals(pos)) {
                    level.setBlock(doorPos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        } finally {
            internalChange = false;
        }
    }

    private static DoorController findOrCreate(ServerLevel level, BlockPos pos) {
        for (DoorController door : DOORS) {
            if (door.level == level && door.positions().contains(pos)) {
                return door;
            }
        }

        BlockState state = level.getBlockState(pos);
        Direction facing = getFacing(state);
        if (facing == null) {
            for (int x = -2; x <= 2; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -2; z <= 2; z++) {
                        BlockPos nearbyPos = pos.offset(x, y, z);
                        Direction nearbyFacing = getFacing(level.getBlockState(nearbyPos));
                        if (nearbyFacing != null) {
                            DoorController door = findFromState(level, nearbyPos, nearbyFacing);
                            if (door != null) {
                                return door;
                            }
                        }
                    }
                }
            }
            return null;
        }

        return findFromState(level, pos, facing);
    }

    private static DoorController findFromState(ServerLevel level, BlockPos pos, Direction facing) {
        for (PartOffset offset : offsets()) {
            int[] rotated = rotate(offset.x, offset.y, offset.z, facing);
            BlockPos base = pos.subtract(new BlockPos(rotated[0], rotated[1], rotated[2]));
            if (level.getBlockState(base).getBlock() == MoreScpAlarmModBlocks.NEWDOOR.get()
                    && level.getBlockState(base).getValue(NewdoorBlock.FACING) == facing) {
                DoorController door = new DoorController(level, base, facing);
                DOORS.add(door);
                return door;
            }
        }
        return null;
    }

    private static Direction getFacing(BlockState state) {
        if (state.getBlock() == MoreScpAlarmModBlocks.NEWDOOR.get()) {
            return state.getValue(NewdoorBlock.FACING);
        }
        if (state.getBlock() == MoreScpAlarmModBlocks.NEWDOOR_PART.get()) {
            return state.getValue(NewdoorPartBlock.FACING);
        }
        return null;
    }

    private static List<PartOffset> offsets() {
        return List.of(
                new PartOffset(0, 0, 0, null),
                new PartOffset(0, -1, 0, NewdoorPartBlock.DoorPart.LEFT_DOWN),
                new PartOffset(0, 1, 0, NewdoorPartBlock.DoorPart.LEFT_UP),
                new PartOffset(-1, 1, 0, NewdoorPartBlock.DoorPart.RIGHT_UP),
                new PartOffset(-1, 0, 0, NewdoorPartBlock.DoorPart.RIGHT_MID),
                new PartOffset(-1, -1, 0, NewdoorPartBlock.DoorPart.RIGHT_DOWN));
    }

    private static int[] rotate(int x, int y, int z, Direction facing) {
        return switch (facing) {
            case EAST -> new int[] { -z, y, x };
            case SOUTH -> new int[] { -x, y, -z };
            case WEST -> new int[] { z, y, -x };
            default -> new int[] { x, y, z };
        };
    }

    private record PartOffset(int x, int y, int z, NewdoorPartBlock.DoorPart part) {
    }

    private static final class DoorController {
        private final ServerLevel level;
        private final BlockPos base;
        private final Direction facing;
        private boolean powered;
        private int slide;
        private int targetSlide;
        private int ticksUntilStep = TICKS_PER_STEP;
        private int animationTicksRemaining;
            private boolean animationPacketSent;

        private DoorController(ServerLevel level, BlockPos base, Direction facing) {
            this.level = level;
            this.base = base;
            this.facing = facing;
        }

        private List<BlockPos> positions() {
            Direction right = facing.getCounterClockWise();
            List<BlockPos> positions = new ArrayList<>();
            for (PartOffset offset : offsets()) {
                int[] rotated = rotate(offset.x, offset.y, offset.z, facing);
                positions.add(base.offset(rotated[0], rotated[1], rotated[2]).relative(right, slide));
            }
            return positions;
        }

        private boolean isValid() {
            List<BlockPos> positions = positions();
            List<PartOffset> offsets = offsets();
            for (int index = 0; index < positions.size(); index++) {
                BlockState state = level.getBlockState(positions.get(index));
                PartOffset offset = offsets.get(index);
                if (offset.part == null) {
                    if (state.getBlock() != MoreScpAlarmModBlocks.NEWDOOR.get()
                            || state.getValue(NewdoorBlock.FACING) != facing) {
                        return false;
                    }
                } else if (state.getBlock() != MoreScpAlarmModBlocks.NEWDOOR_PART.get()
                        || state.getValue(NewdoorPartBlock.FACING) != facing
                        || state.getValue(NewdoorPartBlock.DOOR_PART) != offset.part) {
                    return false;
                }
            }
            return true;
        }

        private boolean moveOneStep() {
            int nextSlide = slide + Integer.signum(targetSlide - slide);
            Direction right = facing.getCounterClockWise();
            List<BlockPos> currentPositions = positions();
            List<BlockPos> nextPositions = new ArrayList<>();
            for (PartOffset offset : offsets()) {
                int[] rotated = rotate(offset.x, offset.y, offset.z, facing);
                nextPositions.add(base.offset(rotated[0], rotated[1], rotated[2]).relative(right, nextSlide));
            }

            for (BlockPos next : nextPositions) {
                if (!currentPositions.contains(next) && !level.getBlockState(next).canBeReplaced()) {
                    return false;
                }
            }

            List<BlockState> states = new ArrayList<>();
            for (BlockPos current : currentPositions) {
                states.add(level.getBlockState(current));
            }
            states.set(0, states.get(0).setValue(NewdoorBlock.ANIMATING, true));
            internalChange = true;
            try {
                for (BlockPos current : currentPositions) {
                    level.setBlock(current, Blocks.AIR.defaultBlockState(), 3);
                }
                for (int index = 0; index < nextPositions.size(); index++) {
                    level.setBlock(nextPositions.get(index), states.get(index), 3);
                }
                slide = nextSlide;
                return true;
            } finally {
                internalChange = false;
            }
        }

        private void finishAnimation() {
            BlockPos mainPos = base.relative(facing.getCounterClockWise(), slide);
            BlockState state = level.getBlockState(mainPos);
            if (state.getBlock() == MoreScpAlarmModBlocks.NEWDOOR.get()) {
                level.setBlock(mainPos, state.setValue(NewdoorBlock.ANIMATING, false), 3);
            }
            resetAnimationState();
        }

        private void markAnimatingImmediately() {
            BlockPos mainPos = base.relative(facing.getCounterClockWise(), slide);
            BlockState state = level.getBlockState(mainPos);
            if (state.getBlock() == MoreScpAlarmModBlocks.NEWDOOR.get()) {
                state = state.setValue(NewdoorBlock.ANIMATING, true);
                internalChange = true;
                try {
                    level.setBlock(mainPos, state, 3);
                } finally {
                    internalChange = false;
                }
            }
        }

        private void sendAnimationPacket() {
            if (!animationPacketSent) {
                animationPacketSent = true;
                int totalDuration = TICKS_PER_STEP * MAX_SLIDE;
                MoreScpAlarmMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(),
                        new BigdoorAnimationMessage(base, facing, slide, targetSlide, totalDuration));
                animationTicksRemaining = totalDuration;
            }
        }

        private void resetAnimationState() {
            animationPacketSent = false;
        }
    }
}
