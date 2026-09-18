package net.theo.scpalarm.procedures;

import net.theo.scpalarm.block.NewdoorPartBlock;
import net.theo.scpalarm.init.MoreScpAlarmModBlocks;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class BigdoorPlacePartProcedure {
    private static int[] rotateCoordinates(int relX, int relY, int relZ, Direction facing) {
        switch (facing) {
            case NORTH:
                return new int[] { relX, relY, relZ };
            case EAST:
                return new int[] { -relZ, relY, relX };
            case SOUTH:
                return new int[] { -relX, relY, -relZ };
            case WEST:
                return new int[] { relZ, relY, -relX };
            default:
                return new int[] { relX, relY, relZ };
        }
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Direction facing) {
        int baseX = (int) x;
        int baseY = (int) y;
        int baseZ = (int) z;

        // Local point of the door:
        // - left = X = 0
        // - right = X = +1
        // - height = Y
        // North Face is the reference.
        placePartAtOffset(world, baseX, baseY, baseZ, facing, 0, -1, 0, NewdoorPartBlock.DoorPart.LEFT_DOWN);
        placePartAtOffset(world, baseX, baseY, baseZ, facing, 0, 1, 0, NewdoorPartBlock.DoorPart.LEFT_UP);
        placePartAtOffset(world, baseX, baseY, baseZ, facing, -1, 1, 0, NewdoorPartBlock.DoorPart.RIGHT_UP);
        placePartAtOffset(world, baseX, baseY, baseZ, facing, -1, 0, 0, NewdoorPartBlock.DoorPart.RIGHT_MID);
        placePartAtOffset(world, baseX, baseY, baseZ, facing, -1, -1, 0, NewdoorPartBlock.DoorPart.RIGHT_DOWN);
    }

    private static void placePartAtOffset(LevelAccessor world, int baseX, int baseY, int baseZ, Direction facing, int relX, int relY, int relZ, NewdoorPartBlock.DoorPart doorPart) {
        int[] rotated = rotateCoordinates(relX, relY, relZ, facing);
        int finalX = baseX + rotated[0];
        int finalY = baseY + rotated[1];
        int finalZ = baseZ + rotated[2];

        BlockState state = MoreScpAlarmModBlocks.NEWDOOR_PART.get().defaultBlockState()
                .setValue(NewdoorPartBlock.FACING, facing)
                .setValue(NewdoorPartBlock.DOOR_PART, doorPart);

        world.setBlock(BlockPos.containing(finalX, finalY, finalZ), state, 3);
    }
}