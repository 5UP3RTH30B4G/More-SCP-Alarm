package net.mcreator.scpalarm.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.scpalarm.network.MoreScpAlarmModVariables;

public class DebugonProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		MoreScpAlarmModVariables.debug = true;
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal("Debug : On"), false);
	}
}
