
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
	public static final RegistryObject<CreativeModeTab> CASSIE = REGISTRY.register("cassie",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.more_scp_alarm.cassie")).icon(() -> new ItemStack(MoreScpAlarmModBlocks.CASSIESIGN.get())).displayItems((parameters, tabData) -> {
				tabData.accept(MoreScpAlarmModBlocks.CASSIESIGN.get().asItem());
			})

					.build());
}
