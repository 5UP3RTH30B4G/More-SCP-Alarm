package net.mcreator.scpalarm.procedures;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.Map;

public class PanellinkProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency entity for procedure Panellink!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
			((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(
					("Here the wiki's link: " + "https://5up3r.gitbook.io/more-scp-alarm-wiki/support/checking-the-in-game-list")), (false));
		}
	}
}
