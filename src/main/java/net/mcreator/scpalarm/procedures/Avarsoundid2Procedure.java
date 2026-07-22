package net.mcreator.scpalarm.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.EditBox;

import net.mcreator.scpalarm.network.MoreScpAlarmModVariables;

import java.util.HashMap;

public class Avarsoundid2Procedure {
	public static void execute(LevelAccessor world, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		MoreScpAlarmModVariables.MapVariables.get(world).alarm2 = guistate.containsKey("text:avaralarm2") ? ((EditBox) guistate.get("text:avaralarm2")).getValue() : "";
		MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
		if (entity instanceof Player _player)
			_player.closeContainer();
		if (MoreScpAlarmModVariables.debug == true) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(("[] DEBUG : Var set to : " + MoreScpAlarmModVariables.MapVariables.get(world).alarm2)), false);
		}
	}
}
