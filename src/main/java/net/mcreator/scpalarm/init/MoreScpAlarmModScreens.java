
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.scpalarm.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.mcreator.scpalarm.client.gui.AvarsetidScreen;
import net.mcreator.scpalarm.client.gui.Avarsetid3Screen;
import net.mcreator.scpalarm.client.gui.Avarsetid2Screen;
import net.mcreator.scpalarm.client.gui.AvarselectorScreen;
import net.mcreator.scpalarm.client.gui.AvarchannelsetScreen;
import net.mcreator.scpalarm.client.gui.AvaralarmguiScreen;
import net.mcreator.scpalarm.client.gui.Avaralarmgui3Screen;
import net.mcreator.scpalarm.client.gui.Avaralarmgui2Screen;
import net.mcreator.scpalarm.client.gui.AlarmpanelScreen;
import net.mcreator.scpalarm.client.gui.ASelectorScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MoreScpAlarmModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(MoreScpAlarmModMenus.A_SELECTOR.get(), ASelectorScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.AVARSELECTOR.get(), AvarselectorScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.AVARALARMGUI.get(), AvaralarmguiScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.AVARSETID.get(), AvarsetidScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.AVARSETID_2.get(), Avarsetid2Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.AVARSETID_3.get(), Avarsetid3Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.AVARALARMGUI_2.get(), Avaralarmgui2Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.AVARALARMGUI_3.get(), Avaralarmgui3Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.AVARCHANNELSET.get(), AvarchannelsetScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.ALARMPANEL.get(), AlarmpanelScreen::new);
		});
	}
}
