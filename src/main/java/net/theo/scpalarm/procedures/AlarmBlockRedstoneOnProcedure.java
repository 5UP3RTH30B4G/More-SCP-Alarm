package net.theo.scpalarm.procedures;

import net.theo.scpalarm.block.entity.AlarmBlockBlockEntity;
import net.theo.scpalarm.network.MoreScpAlarmModVariables;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class AlarmBlockRedstoneOnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world == null || world.isClientSide())
			return;
		BlockPos blockPos = BlockPos.containing(x, y, z);
		if (!(world.getBlockEntity(blockPos) instanceof AlarmBlockBlockEntity blockEntity))
			return;
		String soundId = blockEntity.getSelectedAlarmSound();
		if (soundId.isEmpty())
			return;
		if (world instanceof ServerLevel level) {
			var sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundId));
			if (sound == null)
				return;
			level.playSound(null, blockPos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
		if (MoreScpAlarmModVariables.debug == true) {
			if (!world.isClientSide() && world.getServer() != null)
				world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("executed: playsound " + soundId + " block @a ~ ~ ~"), false);
		}
	}
}
