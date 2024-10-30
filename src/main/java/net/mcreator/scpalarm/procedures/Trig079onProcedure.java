package net.mcreator.scpalarm.procedures;

import net.minecraft.world.IWorld;

import net.mcreator.scpalarm.MoreScpAlarmModVariables;
import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.Map;

public class Trig079onProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency world for procedure Trig079on!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		MoreScpAlarmModVariables.MapVariables.get(world).tv079 = (true);
		MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
	}
}
