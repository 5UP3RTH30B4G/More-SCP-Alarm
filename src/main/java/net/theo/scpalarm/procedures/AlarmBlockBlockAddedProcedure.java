package net.theo.scpalarm.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.theo.scpalarm.network.MoreScpAlarmModVariables;

public class AlarmBlockBlockAddedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!world.isClientSide()) {
			BlockPos _bp = BlockPos.containing(x, y, z);
			MoreScpAlarmModVariables.MapVariables mapVariables = MoreScpAlarmModVariables.MapVariables.get(world);
			String dimension = world instanceof Level level ? level.dimension().location().toString() : "minecraft:overworld";
			String alarmPos = dimension + "@" + _bp.getX() + "," + _bp.getY() + "," + _bp.getZ();
			String storedPositions = mapVariables.AlarmPos;
			if (storedPositions == null || storedPositions.isEmpty() || storedPositions.equals("\"\"")) {
				mapVariables.AlarmPos = alarmPos;
			} else if (!java.util.Arrays.asList(storedPositions.split(";")).contains(alarmPos)
					&& !(dimension.equals("minecraft:overworld") && java.util.Arrays.asList(storedPositions.split(";")).contains(_bp.getX() + "," + _bp.getY() + "," + _bp.getZ()))) {
				mapVariables.AlarmPos = storedPositions + ";" + alarmPos;
			}
			mapVariables.syncData(world);
			BlockEntity _blockEntity = world.getBlockEntity(_bp);
			BlockState _bs = world.getBlockState(_bp);
			if (_blockEntity != null)
				_blockEntity.getPersistentData().putBoolean("AlarmTimerHit", true);
			if (world instanceof Level _level)
				_level.sendBlockUpdated(_bp, _bs, _bs, 3);
		}
	}
}
