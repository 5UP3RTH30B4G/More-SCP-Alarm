package net.mcreator.scpalarm.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.mcreator.scpalarm.network.MoreScpAlarmModVariables;

public class Trig079offProcedure {
	public static void execute(LevelAccessor world) {
		MoreScpAlarmModVariables.MapVariables.get(world).tv079 = false;
		MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
	}
}
