
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.scpalarm.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import net.mcreator.scpalarm.world.inventory.AvarsetidMenu;
import net.mcreator.scpalarm.world.inventory.Avarsetid3Menu;
import net.mcreator.scpalarm.world.inventory.Avarsetid2Menu;
import net.mcreator.scpalarm.world.inventory.AvarselectorMenu;
import net.mcreator.scpalarm.world.inventory.AvarchannelsetMenu;
import net.mcreator.scpalarm.world.inventory.AvaralarmguiMenu;
import net.mcreator.scpalarm.world.inventory.Avaralarmgui3Menu;
import net.mcreator.scpalarm.world.inventory.Avaralarmgui2Menu;
import net.mcreator.scpalarm.world.inventory.AlarmpanelMenu;
import net.mcreator.scpalarm.world.inventory.ASelectorMenu;
import net.mcreator.scpalarm.MoreScpAlarmMod;

public class MoreScpAlarmModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MoreScpAlarmMod.MODID);
	public static final RegistryObject<MenuType<ASelectorMenu>> A_SELECTOR = REGISTRY.register("a_selector", () -> IForgeMenuType.create(ASelectorMenu::new));
	public static final RegistryObject<MenuType<AvarselectorMenu>> AVARSELECTOR = REGISTRY.register("avarselector", () -> IForgeMenuType.create(AvarselectorMenu::new));
	public static final RegistryObject<MenuType<AvaralarmguiMenu>> AVARALARMGUI = REGISTRY.register("avaralarmgui", () -> IForgeMenuType.create(AvaralarmguiMenu::new));
	public static final RegistryObject<MenuType<AvarsetidMenu>> AVARSETID = REGISTRY.register("avarsetid", () -> IForgeMenuType.create(AvarsetidMenu::new));
	public static final RegistryObject<MenuType<Avarsetid2Menu>> AVARSETID_2 = REGISTRY.register("avarsetid_2", () -> IForgeMenuType.create(Avarsetid2Menu::new));
	public static final RegistryObject<MenuType<Avarsetid3Menu>> AVARSETID_3 = REGISTRY.register("avarsetid_3", () -> IForgeMenuType.create(Avarsetid3Menu::new));
	public static final RegistryObject<MenuType<Avaralarmgui2Menu>> AVARALARMGUI_2 = REGISTRY.register("avaralarmgui_2", () -> IForgeMenuType.create(Avaralarmgui2Menu::new));
	public static final RegistryObject<MenuType<Avaralarmgui3Menu>> AVARALARMGUI_3 = REGISTRY.register("avaralarmgui_3", () -> IForgeMenuType.create(Avaralarmgui3Menu::new));
	public static final RegistryObject<MenuType<AvarchannelsetMenu>> AVARCHANNELSET = REGISTRY.register("avarchannelset", () -> IForgeMenuType.create(AvarchannelsetMenu::new));
	public static final RegistryObject<MenuType<AlarmpanelMenu>> ALARMPANEL = REGISTRY.register("alarmpanel", () -> IForgeMenuType.create(AlarmpanelMenu::new));
}
