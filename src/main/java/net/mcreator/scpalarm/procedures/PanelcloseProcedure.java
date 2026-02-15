package net.mcreator.scpalarm.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;

import net.mcreator.scpalarm.network.MoreScpAlarmModVariables;

import java.util.HashMap;

public class PanelcloseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		if (guistate.containsKey("checkbox:channel1") && ((Checkbox) guistate.get("checkbox:channel1")).selected()) {
			MoreScpAlarmModVariables.MapVariables.get(world).alarm1 = guistate.containsKey("text:panel_soundid") ? ((EditBox) guistate.get("text:panel_soundid")).getValue() : "";
			MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("Channel 1 set to :" + MoreScpAlarmModVariables.MapVariables.get(world).alarm1)), false);
			}
		}
		if (guistate.containsKey("checkbox:channel2") && ((Checkbox) guistate.get("checkbox:channel2")).selected()) {
			MoreScpAlarmModVariables.MapVariables.get(world).alarm2 = guistate.containsKey("text:panel_soundid") ? ((EditBox) guistate.get("text:panel_soundid")).getValue() : "";
			MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("Channel 2 set to :" + MoreScpAlarmModVariables.MapVariables.get(world).alarm2)), false);
			}
		}
		if (guistate.containsKey("checkbox:channel3") && ((Checkbox) guistate.get("checkbox:channel3")).selected()) {
			MoreScpAlarmModVariables.MapVariables.get(world).alarm3 = guistate.containsKey("text:panel_soundid") ? ((EditBox) guistate.get("text:panel_soundid")).getValue() : "";
			MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("Channel 3 set to :" + MoreScpAlarmModVariables.MapVariables.get(world).alarm3)), false);
			}
		}
		if (guistate.containsKey("checkbox:panel_playsound") && ((Checkbox) guistate.get("checkbox:panel_playsound")).selected()) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("playsound more_scp_alarm:" + (guistate.containsKey("text:panel_soundid") ? ((EditBox) guistate.get("text:panel_soundid")).getValue() : "") + " master @a ~ ~ ~ 20000"));
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("playsound more_scp_alarm:" + (guistate.containsKey("text:panel_soundid") ? ((EditBox) guistate.get("text:panel_soundid")).getValue() : "") + " master @a ~ ~ ~ 20000")), false);
			}
		}
	}
}
