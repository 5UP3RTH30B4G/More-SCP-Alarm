
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.theo.scpalarm.init;

import net.theo.scpalarm.MoreScpAlarmMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class MoreScpAlarmModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreScpAlarmMod.MODID);
	public static final RegistryObject<CreativeModeTab> CB_LABEL = REGISTRY.register("cb_label",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.more_scp_alarm.cb_label")).icon(() -> new ItemStack(MoreScpAlarmModBlocks.LABEL_012.get())).displayItems((parameters, tabData) -> {
				tabData.accept(MoreScpAlarmModBlocks.LABEL_008.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_012.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_002.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_005.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_006.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_009.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_015.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_019.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_020.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_023.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.POSTER_294.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.ALARM.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_027.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_049.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_053.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_059FR.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_063.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_079.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_087.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_096.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_098.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_1000.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_1025.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_1032_RU.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_1048.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_1057.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_106.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_1074.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_109.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_111.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_1162.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_124.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_131.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_143FR.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_143.get().asItem());
			}).withSearchBar().build());
}
