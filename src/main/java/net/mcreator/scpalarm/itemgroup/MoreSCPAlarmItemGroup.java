
package net.mcreator.scpalarm.itemgroup;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

import net.mcreator.scpalarm.item.LogoItem;
import net.mcreator.scpalarm.MoreScpAlarmModElements;

@MoreScpAlarmModElements.ModElement.Tag
public class MoreSCPAlarmItemGroup extends MoreScpAlarmModElements.ModElement {
	public MoreSCPAlarmItemGroup(MoreScpAlarmModElements instance) {
		super(instance, 5);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabmore_scp_alarm") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(LogoItem.block);
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}

	public static ItemGroup tab;
}
