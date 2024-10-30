package net.mcreator.scpalarm.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.scpalarm.MoreScpAlarmModVariables;
import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.Map;

public class TestgenProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency world for procedure Testgen!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency x for procedure Testgen!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency y for procedure Testgen!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency z for procedure Testgen!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				MoreScpAlarmMod.LOGGER.warn("Failed to load dependency entity for procedure Testgen!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		double timer = 0;
		if (timer >= 53) {
			if (world instanceof World && !world.isRemote()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("more_scp_alarm:generator-count")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
			} else {
				((World) world).playSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("more_scp_alarm:generator-count")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
			}
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Executed."), (false));
				}
			}
			if (MoreScpAlarmModVariables.gencount > 5) {
				MoreScpAlarmModVariables.gencount = (MoreScpAlarmModVariables.gencount + 1);
			}
			timer = 0;
		} else {
			if (MoreScpAlarmModVariables.debug == true) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(
							new StringTextComponent(("Number of generator active: " + MoreScpAlarmModVariables.gencount)), (false));
				}
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Done."), (false));
				}
			}
			timer = (timer + 1);
		}
	}
}
