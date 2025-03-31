package net.mcreator.scpalarm.procedures;

import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.client.gui.widget.TextFieldWidget;

import net.mcreator.scpalarm.MoreScpAlarmModVariables;
import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.Map;
import java.util.HashMap;

public class TestpanelclosesProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency world for procedure Testpanelcloses!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency entity for procedure Testpanelcloses!");
			return;
		}
		if (dependencies.get("guistate") == null) {
			if (!dependencies.containsKey("guistate"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency guistate for procedure Testpanelcloses!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
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
					TextFieldWidget _tf = (TextFieldWidget) guistate.get("text:soundid");
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
							new StringTextComponent(("Channel 1 set to : " + MoreScpAlarmModVariables.MapVariables.get(world).alarm1)), (false));
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
					TextFieldWidget _tf = (TextFieldWidget) guistate.get("text:soundid");
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
							new StringTextComponent(("Channel 2 set to : " + MoreScpAlarmModVariables.MapVariables.get(world).alarm2)), (false));
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
					TextFieldWidget _tf = (TextFieldWidget) guistate.get("text:soundid");
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
							new StringTextComponent(("Channel 3 set to : " + MoreScpAlarmModVariables.MapVariables.get(world).alarm3)), (false));
				}
			}
		}
	}
}
