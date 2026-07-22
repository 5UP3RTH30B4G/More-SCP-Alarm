
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
				tabData.accept(MoreScpAlarmModBlocks.ALARMSENDER.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.TVCHECKPOINT.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.TVLIGHT.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.TRIG_079.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.TV_895.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.NEWDOOR.get().asItem());
			})

					.build());
	public static final RegistryObject<CreativeModeTab> PAPER_CB = REGISTRY.register("paper_cb",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.more_scp_alarm.paper_cb")).icon(() -> new ItemStack(MoreScpAlarmModItems.DOCSTRANGE.get())).displayItems((parameters, tabData) -> {
				tabData.accept(MoreScpAlarmModItems.DOCSTRANGE.get());
				tabData.accept(MoreScpAlarmModItems.DOC_008.get());
				tabData.accept(MoreScpAlarmModItems.DOC_012.get());
				tabData.accept(MoreScpAlarmModItems.DOC_035.get());
				tabData.accept(MoreScpAlarmModItems.DOC_035AD.get());
				tabData.accept(MoreScpAlarmModItems.DOC_038.get());
				tabData.accept(MoreScpAlarmModItems.DOC_049.get());
				tabData.accept(MoreScpAlarmModItems.DOC_079.get());
				tabData.accept(MoreScpAlarmModItems.DOC_93ARM.get());
				tabData.accept(MoreScpAlarmModItems.DOC_096.get());
				tabData.accept(MoreScpAlarmModItems.DOC_1048.get());
				tabData.accept(MoreScpAlarmModItems.DOC_1048A.get());
				tabData.accept(MoreScpAlarmModItems.DOC_106.get());
				tabData.accept(MoreScpAlarmModItems.DOC_1062.get());
				tabData.accept(MoreScpAlarmModItems.DOC_109.get());
				tabData.accept(MoreScpAlarmModItems.DOC_1123.get());
				tabData.accept(MoreScpAlarmModItems.DOC_1162.get());
				tabData.accept(MoreScpAlarmModItems.DOC_1499.get());
				tabData.accept(MoreScpAlarmModItems.DOC_173.get());
				tabData.accept(MoreScpAlarmModItems.DOC_372.get());
				tabData.accept(MoreScpAlarmModItems.DOC_427.get());
				tabData.accept(MoreScpAlarmModItems.DOC_500.get());
				tabData.accept(MoreScpAlarmModItems.DOC_513.get());
				tabData.accept(MoreScpAlarmModItems.DOC_682.get());
				tabData.accept(MoreScpAlarmModItems.DOC_714.get());
				tabData.accept(MoreScpAlarmModItems.DOC_860.get());
				tabData.accept(MoreScpAlarmModItems.DOC_8601.get());
				tabData.accept(MoreScpAlarmModItems.DOC_895.get());
				tabData.accept(MoreScpAlarmModItems.DOC_914.get());
				tabData.accept(MoreScpAlarmModItems.DOC_939.get());
				tabData.accept(MoreScpAlarmModItems.DOC_966.get());
				tabData.accept(MoreScpAlarmModItems.DOC_970.get());
				tabData.accept(MoreScpAlarmModItems.DOCAC.get());
				tabData.accept(MoreScpAlarmModItems.DOCDAN.get());
				tabData.accept(MoreScpAlarmModItems.DOCGONZALES.get());
				tabData.accept(MoreScpAlarmModItems.DOCL_1.get());
				tabData.accept(MoreScpAlarmModItems.DOCL_2.get());
				tabData.accept(MoreScpAlarmModItems.DOCL_3.get());
				tabData.accept(MoreScpAlarmModItems.DOCL_4.get());
				tabData.accept(MoreScpAlarmModItems.DOCL_5.get());
				tabData.accept(MoreScpAlarmModItems.DOCL_6.get());
				tabData.accept(MoreScpAlarmModItems.DOCMAP.get());
				tabData.accept(MoreScpAlarmModItems.DOCMSP.get());
				tabData.accept(MoreScpAlarmModItems.DOCMTF.get());
				tabData.accept(MoreScpAlarmModItems.DOCNDP.get());
				tabData.accept(MoreScpAlarmModItems.DOCBJC.get());
				tabData.accept(MoreScpAlarmModItems.DOCORI.get());
				tabData.accept(MoreScpAlarmModItems.DOCRAND_1.get());
				tabData.accept(MoreScpAlarmModItems.GUIRAND_2.get());
				tabData.accept(MoreScpAlarmModItems.GUIRAND_3.get());
				tabData.accept(MoreScpAlarmModItems.DOCRP.get());
				tabData.accept(MoreScpAlarmModItems.DOCSC.get());
				tabData.accept(MoreScpAlarmModItems.DOCARCE.get());
				tabData.accept(MoreScpAlarmModItems.DOCF_5.get());
				tabData.accept(MoreScpAlarmModItems.DOCF_4.get());
				tabData.accept(MoreScpAlarmModItems.DOCF_6.get());
				tabData.accept(MoreScpAlarmModItems.DOCIR_106.get());
			}).withSearchBar().build());
	public static final RegistryObject<CreativeModeTab> CASSIE = REGISTRY.register("cassie",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.more_scp_alarm.cassie")).icon(() -> new ItemStack(MoreScpAlarmModBlocks.CASSIESIGN.get())).displayItems((parameters, tabData) -> {
				tabData.accept(MoreScpAlarmModBlocks.CASSIESIGN.get().asItem());
			})

					.build());
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
				tabData.accept(MoreScpAlarmModBlocks.ALARM.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_111.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_1162.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_124.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_131.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_143FR.get().asItem());
				tabData.accept(MoreScpAlarmModBlocks.LABEL_143.get().asItem());
			}).withSearchBar().build());
}
