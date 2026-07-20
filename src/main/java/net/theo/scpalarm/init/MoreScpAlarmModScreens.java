
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.theo.scpalarm.init;

import net.theo.scpalarm.client.gui.GuistrangeScreen;
import net.theo.scpalarm.client.gui.GuiscScreen;
import net.theo.scpalarm.client.gui.GuirpScreen;
import net.theo.scpalarm.client.gui.Guirand1Screen;
import net.theo.scpalarm.client.gui.GuioriScreen;
import net.theo.scpalarm.client.gui.GuindpScreen;
import net.theo.scpalarm.client.gui.GuimtfScreen;
import net.theo.scpalarm.client.gui.GuimspScreen;
import net.theo.scpalarm.client.gui.GuimapScreen;
import net.theo.scpalarm.client.gui.Guil6Screen;
import net.theo.scpalarm.client.gui.Guil5Screen;
import net.theo.scpalarm.client.gui.Guil4Screen;
import net.theo.scpalarm.client.gui.Guil3Screen;
import net.theo.scpalarm.client.gui.Guil2Screen;
import net.theo.scpalarm.client.gui.Guil1Screen;
import net.theo.scpalarm.client.gui.Guiir106Screen;
import net.theo.scpalarm.client.gui.GuigonzalesScreen;
import net.theo.scpalarm.client.gui.Guif6Screen;
import net.theo.scpalarm.client.gui.Guif5Screen;
import net.theo.scpalarm.client.gui.Guif4Screen;
import net.theo.scpalarm.client.gui.GuidanScreen;
import net.theo.scpalarm.client.gui.GuibjoScreen;
import net.theo.scpalarm.client.gui.GuiarceScreen;
import net.theo.scpalarm.client.gui.GuiacScreen;
import net.theo.scpalarm.client.gui.Gui970Screen;
import net.theo.scpalarm.client.gui.Gui966Screen;
import net.theo.scpalarm.client.gui.Gui93armScreen;
import net.theo.scpalarm.client.gui.Gui939Screen;
import net.theo.scpalarm.client.gui.Gui914Screen;
import net.theo.scpalarm.client.gui.Gui895Screen;
import net.theo.scpalarm.client.gui.Gui860Screen;
import net.theo.scpalarm.client.gui.Gui8601Screen;
import net.theo.scpalarm.client.gui.Gui714Screen;
import net.theo.scpalarm.client.gui.Gui682Screen;
import net.theo.scpalarm.client.gui.Gui513Screen;
import net.theo.scpalarm.client.gui.Gui500Screen;
import net.theo.scpalarm.client.gui.Gui427Screen;
import net.theo.scpalarm.client.gui.Gui372Screen;
import net.theo.scpalarm.client.gui.Gui173Screen;
import net.theo.scpalarm.client.gui.Gui1499Screen;
import net.theo.scpalarm.client.gui.Gui1162Screen;
import net.theo.scpalarm.client.gui.Gui1123Screen;
import net.theo.scpalarm.client.gui.Gui109Screen;
import net.theo.scpalarm.client.gui.Gui106Screen;
import net.theo.scpalarm.client.gui.Gui1062Screen;
import net.theo.scpalarm.client.gui.Gui1048aScreen;
import net.theo.scpalarm.client.gui.Gui1048Screen;
import net.theo.scpalarm.client.gui.Gui096Screen;
import net.theo.scpalarm.client.gui.Gui079Screen;
import net.theo.scpalarm.client.gui.Gui049Screen;
import net.theo.scpalarm.client.gui.Gui038Screen;
import net.theo.scpalarm.client.gui.Gui035adScreen;
import net.theo.scpalarm.client.gui.Gui035Screen;
import net.theo.scpalarm.client.gui.Gui012Screen;
import net.theo.scpalarm.client.gui.Gui008Screen;
import net.theo.scpalarm.client.gui.Docrand3Screen;
import net.theo.scpalarm.client.gui.Docrand2Screen;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MoreScpAlarmModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(MoreScpAlarmModMenus.GUI_008.get(), Gui008Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUISTRANGE.get(), GuistrangeScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_012.get(), Gui012Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_035.get(), Gui035Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_035AD.get(), Gui035adScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_038.get(), Gui038Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_049.get(), Gui049Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_079.get(), Gui079Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_93ARM.get(), Gui93armScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_096.get(), Gui096Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_1048.get(), Gui1048Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_1048A.get(), Gui1048aScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_106.get(), Gui106Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_1062.get(), Gui1062Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_109.get(), Gui109Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_1123.get(), Gui1123Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_1162.get(), Gui1162Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_1499.get(), Gui1499Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_173.get(), Gui173Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_372.get(), Gui372Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_427.get(), Gui427Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_500.get(), Gui500Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_513.get(), Gui513Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_682.get(), Gui682Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_714.get(), Gui714Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_860.get(), Gui860Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_8601.get(), Gui8601Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_895.get(), Gui895Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_914.get(), Gui914Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_939.get(), Gui939Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_966.get(), Gui966Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUI_970.get(), Gui970Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIAC.get(), GuiacScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIDAN.get(), GuidanScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIGONZALES.get(), GuigonzalesScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIIR_106.get(), Guiir106Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIL_1.get(), Guil1Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIMAP.get(), GuimapScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIMSP.get(), GuimspScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIMTF.get(), GuimtfScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUINDP.get(), GuindpScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIBJC.get(), GuibjoScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIORI.get(), GuioriScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIRAND_1.get(), Guirand1Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIRP.get(), GuirpScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUISC.get(), GuiscScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIARCE.get(), GuiarceScreen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIF_4.get(), Guif4Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIF_5.get(), Guif5Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIF_6.get(), Guif6Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIL_2.get(), Guil2Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIL_3.get(), Guil3Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIL_4.get(), Guil4Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIL_5.get(), Guil5Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.GUIL_6.get(), Guil6Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.DOCRAND_2.get(), Docrand2Screen::new);
			MenuScreens.register(MoreScpAlarmModMenus.DOCRAND_3.get(), Docrand3Screen::new);
		});
	}
}
