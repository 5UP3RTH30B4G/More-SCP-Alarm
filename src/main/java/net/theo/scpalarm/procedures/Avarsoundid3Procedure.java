package net.theo.scpalarm.procedures;

import net.theo.scpalarm.network.MoreScpAlarmModVariables;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.EditBox;

import java.util.HashMap;

public class Avarsoundid3Procedure {
	public static void execute(LevelAccessor world, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		MoreScpAlarmModVariables.MapVariables.get(world).alarm3 = guistate.containsKey("text:avaralarm3") ? ((EditBox) guistate.get("text:avaralarm3")).getValue() : "";
		MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
		if (entity instanceof Player _player)
			_player.closeContainer();
		if (MoreScpAlarmModVariables.debug == true) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(("[] DEBUG : Var set to : " + MoreScpAlarmModVariables.MapVariables.get(world).alarm3)), false);
		}
	}
}
