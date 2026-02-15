package net.mcreator.scpalarm.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.scpalarm.network.MoreScpAlarmModVariables;

public class DebugoffProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		MoreScpAlarmModVariables.debug = false;
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal("Debug : Off"), false);
	}
}
