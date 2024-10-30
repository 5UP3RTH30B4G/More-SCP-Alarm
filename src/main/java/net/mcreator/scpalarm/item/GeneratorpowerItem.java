
package net.mcreator.scpalarm.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.UseAction;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.scpalarm.itemgroup.MoreSCPAlarmItemGroup;
import net.mcreator.scpalarm.MoreScpAlarmModElements;

@MoreScpAlarmModElements.ModElement.Tag
public class GeneratorpowerItem extends MoreScpAlarmModElements.ModElement {
	@ObjectHolder("more_scp_alarm:generatortablet")
	public static final Item block = null;

	public GeneratorpowerItem(MoreScpAlarmModElements instance) {
		super(instance, 28);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(MoreSCPAlarmItemGroup.tab).maxStackSize(1).rarity(Rarity.UNCOMMON));
			setRegistryName("generatortablet");
		}

		@Override
		public UseAction getUseAction(ItemStack itemstack) {
			return UseAction.EAT;
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
