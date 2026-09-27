package net.theo.scpalarm.procedures;

import net.theo.scpalarm.network.MoreScpAlarmModVariables;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class AlarmBlockBlockDestroyedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world == null || world.isClientSide())
			return;

		BlockPos blockPos = BlockPos.containing(x, y, z);
		String destroyedPos = blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ();
		MoreScpAlarmModVariables.MapVariables mapVariables = MoreScpAlarmModVariables.MapVariables.get(world);
		String storedPositions = mapVariables.AlarmPos;
		if (storedPositions == null || storedPositions.isEmpty() || storedPositions.equals("\"\""))
			return;

		java.util.List<String> remainingPositions = new java.util.ArrayList<>();
		for (String position : storedPositions.split(";")) {
			if (!position.equals(destroyedPos))
				remainingPositions.add(position);
		}
		if (remainingPositions.size() != storedPositions.split(";").length) {
			mapVariables.AlarmPos = String.join(";", remainingPositions);
			mapVariables.syncData(world);
		}
	}
}
