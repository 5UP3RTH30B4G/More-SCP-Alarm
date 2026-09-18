package net.theo.scpalarm.procedures;

import net.theo.scpalarm.network.MoreScpAlarmModVariables;

import net.minecraft.world.level.LevelAccessor;

public class TV079TriggerRedstoneOffProcedure {
	public static void execute(LevelAccessor world) {
		MoreScpAlarmModVariables.MapVariables.get(world).tv079 = false;
		MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
	}
}
