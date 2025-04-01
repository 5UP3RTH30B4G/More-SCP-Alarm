package net.mcreator.scpalarm.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.client.gui.widget.TextFieldWidget;

import net.mcreator.scpalarm.MoreScpAlarmModVariables;
import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.Map;
import java.util.HashMap;

public class PanelcloseProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency world for procedure Panelclose!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency x for procedure Panelclose!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency y for procedure Panelclose!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency z for procedure Panelclose!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency entity for procedure Panelclose!");
			return;
		}
		if (dependencies.get("guistate") == null) {
			if (!dependencies.containsKey("guistate"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency guistate for procedure Panelclose!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		HashMap guistate = (HashMap) dependencies.get("guistate");
		if (new Object() {
			public boolean getValue() {
				CheckboxButton checkbox = (CheckboxButton) guistate.get("checkbox:channel1");
				if (checkbox != null) {
					return checkbox.isChecked();
				}
				return false;
			}
		}.getValue()) {
			MoreScpAlarmModVariables.MapVariables.get(world).alarm1 = (new Object() {
				public String getText() {
					TextFieldWidget _tf = (TextFieldWidget) guistate.get("text:panel_soundid");
					if (_tf != null) {
						return _tf.getText();
					}
					return "";
				}
			}.getText());
			MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(
							new StringTextComponent(("Channel 1 set to :" + MoreScpAlarmModVariables.MapVariables.get(world).alarm1)), (false));
				}
			}
		}
		if (new Object() {
			public boolean getValue() {
				CheckboxButton checkbox = (CheckboxButton) guistate.get("checkbox:channel2");
				if (checkbox != null) {
					return checkbox.isChecked();
				}
				return false;
			}
		}.getValue()) {
			MoreScpAlarmModVariables.MapVariables.get(world).alarm2 = (new Object() {
				public String getText() {
					TextFieldWidget _tf = (TextFieldWidget) guistate.get("text:panel_soundid");
					if (_tf != null) {
						return _tf.getText();
					}
					return "";
				}
			}.getText());
			MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(
							new StringTextComponent(("Channel 2 set to :" + MoreScpAlarmModVariables.MapVariables.get(world).alarm2)), (false));
				}
			}
		}
		if (new Object() {
			public boolean getValue() {
				CheckboxButton checkbox = (CheckboxButton) guistate.get("checkbox:channel3");
				if (checkbox != null) {
					return checkbox.isChecked();
				}
				return false;
			}
		}.getValue()) {
			MoreScpAlarmModVariables.MapVariables.get(world).alarm3 = (new Object() {
				public String getText() {
					TextFieldWidget _tf = (TextFieldWidget) guistate.get("text:panel_soundid");
					if (_tf != null) {
						return _tf.getText();
					}
					return "";
				}
			}.getText());
			MoreScpAlarmModVariables.MapVariables.get(world).syncData(world);
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(
							new StringTextComponent(("Channel 3 set to :" + MoreScpAlarmModVariables.MapVariables.get(world).alarm3)), (false));
				}
			}
		}
		if (new Object() {
			public boolean getValue() {
				CheckboxButton checkbox = (CheckboxButton) guistate.get("checkbox:panel_playsound");
				if (checkbox != null) {
					return checkbox.isChecked();
				}
				return false;
			}
		}.getValue()) {
			if (world instanceof ServerWorld) {
				((World) world).getServer().getCommandManager().handleCommand(
						new CommandSource(ICommandSource.DUMMY, new Vector3d(x, y, z), Vector2f.ZERO, (ServerWorld) world, 4, "",
								new StringTextComponent(""), ((World) world).getServer(), null).withFeedbackDisabled(),
						("playsound more_scp_alarm:" + (new Object() {
							public String getText() {
								TextFieldWidget _tf = (TextFieldWidget) guistate.get("text:panel_soundid");
								if (_tf != null) {
									return _tf.getText();
								}
								return "";
							}
						}.getText()) + " master @a ~ ~ ~ 20000"));
			}
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(("playsound more_scp_alarm:" + (new Object() {
						public String getText() {
							TextFieldWidget _tf = (TextFieldWidget) guistate.get("text:panel_soundid");
							if (_tf != null) {
								return _tf.getText();
							}
							return "";
						}
					}.getText()) + " master @a ~ ~ ~ 20000")), (false));
				}
			}
		}
	}
}
