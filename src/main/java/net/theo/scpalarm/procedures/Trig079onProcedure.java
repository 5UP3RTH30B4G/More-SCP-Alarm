package net.theo.scpalarm.procedures;

import net.theo.scpalarm.network.MoreScpAlarmModVariables;

import net.minecraft.world.level.LevelAccessor;

public class Trig079onProcedure {
	public static void execute(LevelAccessor world) {
		MoreScpAlarmModVariables.MapVariables.get(world).tv079 = true;
		MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
	}
}
