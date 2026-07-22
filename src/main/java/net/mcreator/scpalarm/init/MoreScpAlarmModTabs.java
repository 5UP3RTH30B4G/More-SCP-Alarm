
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.scpalarm.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.scpalarm.MoreScpAlarmMod;

public class MoreScpAlarmModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreScpAlarmMod.MODID);
	public static final RegistryObject<CreativeModeTab> MORE_SCP_ALARM = REGISTRY.register("more_scp_alarm",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.more_scp_alarm.more_scp_alarm")).icon(() -> new ItemStack(MoreScpAlarmModItems.LOGO.get())).displayItems((parameters, tabData) -> {
				tabData.accept(MoreScpAlarmModBlocks.GAZ.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.GATE_1.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.GATE_2.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.GATE_3.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.GATE_4.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.DOOR_5.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.DOOR_6.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.DOOR_7.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.DOOR_8.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.DOOR_FRAME.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.ALARM.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.ALARMSENDER.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.TVCHECKPOINT.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.TVLIGHT.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.TRIG_079.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.TV_895.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.NEWDOOR.get().asItem());
			})

					.build());
}
