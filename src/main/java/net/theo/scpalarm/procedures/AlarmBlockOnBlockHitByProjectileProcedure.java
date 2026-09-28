package net.theo.scpalarm.procedures;

import net.theo.scpalarm.network.MoreScpAlarmModVariables;
import net.theo.scpalarm.MoreScpAlarmMod;
import net.theo.scpalarm.block.entity.AlarmBlockBlockEntity;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.registries.ForgeRegistries;

public class AlarmBlockOnBlockHitByProjectileProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((new Object() {
			public boolean getValue(LevelAccessor world, BlockPos pos, String tag) {
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity != null)
					return blockEntity.getPersistentData().getBoolean(tag);
				return false;
			}
		}.getValue(world, BlockPos.containing(x, y, z), "AlarmTimerHit")) == true) {
			if (world instanceof ServerLevel _level) {
				BlockPos alarmPos = BlockPos.containing(x, y, z);
				BlockEntity alarmEntity = _level.getBlockEntity(alarmPos);
				boolean globalMode = !(alarmEntity instanceof AlarmBlockBlockEntity alarmBlock) || alarmBlock.isGlobalMode();
				String selectedSound = alarmEntity instanceof AlarmBlockBlockEntity alarmBlock ? alarmBlock.getSelectedAlarmSound() : "";
				ResourceLocation soundId = ResourceLocation.tryParse(selectedSound);
				SoundEvent selectedEvent = soundId == null || globalMode ? null : ForgeRegistries.SOUND_EVENTS.getValue(soundId);
				if (selectedEvent != null && soundId.getNamespace().equals("more_scp_alarm") && !soundId.getPath().toLowerCase(java.util.Locale.ROOT).startsWith("cassie_")) {
					_level.playSound(null, alarmPos, selectedEvent, SoundSource.BLOCKS, 1, 1);
				} else {
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"playsound more_scp_alarm:scp-079-testroom block ~ ~ ~ @a 1 1.5");
				}
			}
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null)
					_blockEntity.getPersistentData().putBoolean("AlarmTimerHit", false);
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (MoreScpAlarmModVariables.debug == true) {
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("Alarm Hit: " + world.getBlockState(BlockPos.containing(x, y, z)) + " Timer Started")), false);
			}
			MoreScpAlarmMod.queueServerWork(6000, () -> {
				if (!world.isClientSide()) {
					BlockPos _bp = BlockPos.containing(x, y, z);
					BlockEntity _blockEntity = world.getBlockEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_blockEntity != null)
						_blockEntity.getPersistentData().putBoolean("AlarmTimerHit", true);
					if (world instanceof Level _level)
						_level.sendBlockUpdated(_bp, _bs, _bs, 3);
				}
				if (MoreScpAlarmModVariables.debug == true) {
					if (!world.isClientSide() && world.getServer() != null)
						world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(("Alarm Hit: " + world.getBlockState(BlockPos.containing(x, y, z)) + " Timer Reset")), false);
				}
			});
		}
	}
}
