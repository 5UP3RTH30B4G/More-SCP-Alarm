
package net.theo.scpalarm.world.inventory;

import net.theo.scpalarm.init.MoreScpAlarmModMenus;
import net.theo.scpalarm.network.MoreScpAlarmModVariables;
import net.theo.scpalarm.procedures.AlarmGlobalSoundProcedure;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class AlarmGlobalPanelMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
	public static final HashMap<String, Object> guistate = new HashMap<>();
	public final Level world;
	public final Player entity;
	public final int x, y, z;
	private final Map<Integer, Slot> customSlots = new HashMap<>();

	public AlarmGlobalPanelMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
		super(MoreScpAlarmModMenus.ALARM_GLOBAL_PANEL.get(), id);
		this.entity = inventory.player;
		this.world = inventory.player.level();
		BlockPos panelPos = extraData != null ? extraData.readBlockPos() : inventory.player.blockPosition();
		this.x = panelPos.getX();
		this.y = panelPos.getY();
		this.z = panelPos.getZ();
	}

	@Override
	public boolean stillValid(Player player) {
		return player.level() == this.world;
	}

	public String getGlobalAlarmSound() {
		return MoreScpAlarmModVariables.MapVariables.get(this.world).GlobalAlarmSound;
	}

	@Override
	public boolean clickMenuButton(Player player, int buttonId) {
		if (!this.stillValid(player))
			return false;
		List<ResourceLocation> sounds = AlarmGUIMenu.getAvailableSounds();
		if (buttonId == sounds.size()) {
			int playedAt = AlarmGlobalSoundProcedure.play(this.world, this.x, this.y, this.z);
			if (MoreScpAlarmModVariables.debug)
				player.sendSystemMessage(net.minecraft.network.chat.Component.literal("[Alarm debug] Test global: " + playedAt + " position(s)"));
			return true;
		}
		if (buttonId < 0 || buttonId >= sounds.size())
			return false;
		MoreScpAlarmModVariables.MapVariables mapVariables = MoreScpAlarmModVariables.MapVariables.get(this.world);
		mapVariables.GlobalAlarmSound = sounds.get(buttonId).toString();
		mapVariables.syncData(this.world);
		if (MoreScpAlarmModVariables.debug)
			player.sendSystemMessage(net.minecraft.network.chat.Component.literal("[Alarm debug] Son global: " + mapVariables.GlobalAlarmSound));
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public Map<Integer, Slot> get() {
		return this.customSlots;
	}
}
