
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.theo.scpalarm.init;

import org.lwjgl.glfw.GLFW;

import net.theo.scpalarm.network.AlarmGlobalPanelOpenKeybindMessage;
import net.theo.scpalarm.MoreScpAlarmMod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class MoreScpAlarmModKeyMappings {
	public static final KeyMapping ALARM_GLOBAL_PANEL_OPEN_KEYBIND = new KeyMapping("key.more_scp_alarm.alarm_global_panel_open_keybind", GLFW.GLFW_KEY_F10, "key.categories.creative") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				MoreScpAlarmMod.PACKET_HANDLER.sendToServer(new AlarmGlobalPanelOpenKeybindMessage(0, 0));
				AlarmGlobalPanelOpenKeybindMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(ALARM_GLOBAL_PANEL_OPEN_KEYBIND);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if (Minecraft.getInstance().screen == null) {
				ALARM_GLOBAL_PANEL_OPEN_KEYBIND.consumeClick();
			}
		}
	}
}
