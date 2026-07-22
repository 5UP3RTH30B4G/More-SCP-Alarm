package net.mcreator.scpalarm.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.scpalarm.network.MoreScpAlarmModVariables;

public class Trig079onProcedure {
	public static void execute(LevelAccessor world) {
		MoreScpAlarmModVariables.MapVariables.get(world).tv079 = true;
		MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
	}
}
