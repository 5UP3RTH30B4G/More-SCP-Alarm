
package net.theo.scpalarm.world.inventory;

import net.theo.scpalarm.MoreScpAlarmMod;
import net.theo.scpalarm.block.entity.AlarmBlockBlockEntity;
import net.theo.scpalarm.init.MoreScpAlarmModMenus;
import net.theo.scpalarm.network.MoreScpAlarmModVariables;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;

public class AlarmGUIMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
	public final static HashMap<String, Object> guistate = new HashMap<>();
	public final Level world;
	public final Player entity;
	public int x, y, z;
	private ContainerLevelAccess access = ContainerLevelAccess.NULL;
	private IItemHandler internal;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	private boolean bound = false;
	private Supplier<Boolean> boundItemMatcher = null;
	private Entity boundEntity = null;
	private BlockEntity boundBlockEntity = null;

	public AlarmGUIMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(MoreScpAlarmModMenus.ALARM_GUI.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.internal = new ItemStackHandler(0);
		BlockPos pos = null;
		if (extraData != null) {
			pos = extraData.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			access = ContainerLevelAccess.create(world, pos);
		}
	}

	@Override
	public boolean stillValid(Player player) {
		BlockPos pos = new BlockPos(this.x, this.y, this.z);
		return this.world.getBlockEntity(pos) instanceof AlarmBlockBlockEntity
				&& AbstractContainerMenu.stillValid(this.access, player, this.world.getBlockState(pos).getBlock());
	}

	public boolean isGlobalMode() {
		return this.world.getBlockEntity(new BlockPos(this.x, this.y, this.z)) instanceof AlarmBlockBlockEntity blockEntity && blockEntity.isGlobalMode();
	}

	public String getSelectedAlarmSound() {
		return this.world.getBlockEntity(new BlockPos(this.x, this.y, this.z)) instanceof AlarmBlockBlockEntity blockEntity ? blockEntity.getSelectedAlarmSound() : "";
	}

	@Override
	public boolean clickMenuButton(Player player, int buttonId) {
		debug(player, "Action recue: bouton " + buttonId);
		if (!this.stillValid(player) || !(this.world.getBlockEntity(new BlockPos(this.x, this.y, this.z)) instanceof AlarmBlockBlockEntity blockEntity)) {
			debug(player, "Action refusee: bloc absent ou menu invalide");
			return false;
		}
		if (buttonId == 0) {
			blockEntity.setGlobalMode(!blockEntity.isGlobalMode());
			debug(player, "Mode " + (blockEntity.isGlobalMode() ? "GLOBAL" : "LOCAL"));
			return true;
		}
		if (blockEntity.isGlobalMode()) {
			debug(player, "Selection refusee: mode GLOBAL");
			return false;
		}
		int soundIndex = buttonId - 1;
		List<ResourceLocation> sounds = getAvailableSounds();
		if (soundIndex < 0 || soundIndex >= sounds.size()) {
			debug(player, "Selection refusee: index son invalide " + soundIndex);
			return false;
		}
		blockEntity.setSelectedAlarmSound(sounds.get(soundIndex).toString());
		debug(player, "Son sauvegarde: " + sounds.get(soundIndex));
		return true;
	}

	private static void debug(Player player, String message) {
		if (MoreScpAlarmModVariables.debug && player instanceof ServerPlayer serverPlayer)
			serverPlayer.sendSystemMessage(Component.literal("[Alarm debug] " + message));
	}

	public static List<ResourceLocation> getAvailableSounds() {
		return ForgeRegistries.SOUND_EVENTS.getKeys().stream()
				.filter(id -> id.getNamespace().equals(MoreScpAlarmMod.MODID))
				.filter(id -> !id.getPath().toLowerCase(java.util.Locale.ROOT).startsWith("cassie_"))
				.sorted(Comparator.comparing(ResourceLocation::getPath))
				.toList();
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		return ItemStack.EMPTY;
	}

	public Map<Integer, Slot> get() {
		return customSlots;
	}
}
