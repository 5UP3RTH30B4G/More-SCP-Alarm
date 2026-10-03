package net.theo.scpalarm.procedures;

import net.theo.scpalarm.MoreScpAlarmMod;
import net.theo.scpalarm.block.entity.AlarmBlockBlockEntity;
import net.theo.scpalarm.network.MoreScpAlarmModVariables;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlarmGlobalSoundProcedure {
	public static int play(LevelAccessor world, double fallbackX, double fallbackY, double fallbackZ) {
		return play(world, fallbackX, fallbackY, fallbackZ, null);
	}

	public static int play(LevelAccessor world, double fallbackX, double fallbackY, double fallbackZ, Player previewPlayer) {
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
		SoundEvent soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(sound);
		if (soundEvent == null)
			return 0;

		String[] positions = variables.AlarmPos == null || variables.AlarmPos.isEmpty() || variables.AlarmPos.equals("\"\"")
				? new String[0] : variables.AlarmPos.split(";");
		Set<String> seenPositions = new HashSet<>();
		List<String> playedPositions = new ArrayList<>();
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
				String positionKey = dimension + "@" + position.getX() + "," + position.getY() + "," + position.getZ();
				if (!seenPositions.add(positionKey))
					continue;
				if (!alarmLevel.hasChunkAt(position) || !(alarmLevel.getBlockEntity(position) instanceof AlarmBlockBlockEntity))
					continue;
				alarmLevel.playSound(null, position, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
				playedPositions.add(positionKey);
				playedAt++;
			} catch (NumberFormatException ignored) {
			}
		}

		if (playedAt == 0) {
			BlockPos fallbackPos = BlockPos.containing(fallbackX, fallbackY, fallbackZ);
			fallbackLevel.playSound(null, fallbackPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
			playedPositions.add(fallbackLevel.dimension().location() + "@" + fallbackPos.getX() + "," + fallbackPos.getY() + "," + fallbackPos.getZ());
			playedAt = 1;
		}
		if (previewPlayer instanceof ServerPlayer serverPlayer)
			serverPlayer.playNotifySound(soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);

		if (MoreScpAlarmModVariables.debug)
			world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(
					"[Alarm debug] Son global " + sound + " joue a " + playedAt + " position(s): " + String.join("; ", playedPositions)), false);
		return playedAt;
	}
}