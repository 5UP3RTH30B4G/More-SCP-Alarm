package net.theo.scpalarm.procedures;

import net.theo.scpalarm.MoreScpAlarmMod;
import net.theo.scpalarm.network.MoreScpAlarmModVariables;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class AlarmGlobalSoundProcedure {
	public static int play(LevelAccessor world, double fallbackX, double fallbackY, double fallbackZ) {
		if (world == null || world.isClientSide() || world.getServer() == null)
			return 0;

		ServerLevel fallbackLevel = world instanceof ServerLevel serverLevel ? serverLevel : world.getServer().overworld();
		MoreScpAlarmModVariables.MapVariables variables = MoreScpAlarmModVariables.MapVariables.get(world);
		ResourceLocation sound = ResourceLocation.tryParse(variables.GlobalAlarmSound);
		if (sound == null || !sound.getNamespace().equals(MoreScpAlarmMod.MODID)
				|| sound.getPath().toLowerCase(java.util.Locale.ROOT).startsWith("cassie_")
				|| ForgeRegistries.SOUND_EVENTS.getValue(sound) == null) {
			sound = new ResourceLocation(MoreScpAlarmMod.MODID, "scp-079-testroom");
		}

		String[] positions = variables.AlarmPos == null || variables.AlarmPos.isEmpty() || variables.AlarmPos.equals("\"\"")
				? new String[0] : variables.AlarmPos.split(";");
		Set<String> playedPositions = new HashSet<>();
		int playedAt = 0;
		for (String storedPosition : positions) {
			try {
				String[] dimensionAndCoordinates = storedPosition.split("@", 2);
				String dimensionId = dimensionAndCoordinates.length == 2 ? dimensionAndCoordinates[0] : "minecraft:overworld";
				ResourceLocation dimension = ResourceLocation.tryParse(dimensionId);
				if (dimension == null)
					continue;
				ServerLevel alarmLevel = null;
				for (ServerLevel candidate : world.getServer().getAllLevels()) {
					if (candidate.dimension().location().equals(dimension)) {
						alarmLevel = candidate;
						break;
					}
				}
				if (alarmLevel == null)
					continue;

				String coordinatesValue = dimensionAndCoordinates.length == 2 ? dimensionAndCoordinates[1] : storedPosition;
				String[] coordinates = coordinatesValue.split(",");
				if (coordinates.length != 3)
					continue;
				BlockPos position = new BlockPos(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[2]));
				String positionKey = dimension + "@" + position;
				if (!playedPositions.add(positionKey))
					continue;

				CommandSourceStack source = sourceAt(alarmLevel, position);
				alarmLevel.getServer().getCommands().performPrefixedCommand(source, "playsound " + sound + " block ~ ~ ~ @a 1 1.5");
				playedAt++;
			} catch (NumberFormatException ignored) {
			}
		}

		if (playedAt == 0) {
			BlockPos fallbackPos = BlockPos.containing(fallbackX, fallbackY, fallbackZ);
			fallbackLevel.getServer().getCommands().performPrefixedCommand(sourceAt(fallbackLevel, fallbackPos), "playsound " + sound + " block ~ ~ ~ @a 1 1.5");
			playedAt = 1;
		}

		if (MoreScpAlarmModVariables.debug)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("[Alarm debug] Son global " + sound + " joue a " + playedAt + " position(s)"), false);
		return playedAt;
	}

	private static CommandSourceStack sourceAt(ServerLevel level, BlockPos position) {
		return new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(position), Vec2.ZERO, level, 4, "", Component.literal(""), level.getServer(), null).withSuppressedOutput();
	}
}