
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.theo.scp.init;

import net.theo.scp.TheoScpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class TheoScpModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheoScpMod.MODID);
	public static final RegistryObject<CreativeModeTab> CASSIE = REGISTRY.register("cassie",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.theo_scp.cassie")).icon(() -> new ItemStack(TheoScpModBlocks.CASSIESIGN.get())).displayItems((parameters, tabData) -> {
				tabData.accept(TheoScpModBlocks.CASSIESIGN.get().asItem());
			})

					.build());
}
