
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.theo.scpalarm.init;

import net.theo.scpalarm.MoreScpAlarmMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

public class MoreScpAlarmModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MoreScpAlarmMod.MODID);
	public static final RegistryObject<Item> LABEL_008 = block(MoreScpAlarmModBlocks.LABEL_008);
	public static final RegistryObject<Item> LABEL_012 = block(MoreScpAlarmModBlocks.LABEL_012);
	public static final RegistryObject<Item> LABEL_002 = block(MoreScpAlarmModBlocks.LABEL_002);
	public static final RegistryObject<Item> LABEL_005 = block(MoreScpAlarmModBlocks.LABEL_005);
	public static final RegistryObject<Item> LABEL_006 = block(MoreScpAlarmModBlocks.LABEL_006);
	public static final RegistryObject<Item> LABEL_009 = block(MoreScpAlarmModBlocks.LABEL_009);
	public static final RegistryObject<Item> LABEL_015 = block(MoreScpAlarmModBlocks.LABEL_015);
	public static final RegistryObject<Item> LABEL_019 = block(MoreScpAlarmModBlocks.LABEL_019);
	public static final RegistryObject<Item> LABEL_020 = block(MoreScpAlarmModBlocks.LABEL_020);
	public static final RegistryObject<Item> LABEL_023 = block(MoreScpAlarmModBlocks.LABEL_023);
	public static final RegistryObject<Item> POSTER_294 = block(MoreScpAlarmModBlocks.POSTER_294);
	public static final RegistryObject<Item> ALARM = block(MoreScpAlarmModBlocks.ALARM);
	public static final RegistryObject<Item> LABEL_027 = block(MoreScpAlarmModBlocks.LABEL_027);
	public static final RegistryObject<Item> LABEL_049 = block(MoreScpAlarmModBlocks.LABEL_049);
	public static final RegistryObject<Item> LABEL_053 = block(MoreScpAlarmModBlocks.LABEL_053);
	public static final RegistryObject<Item> LABEL_059FR = block(MoreScpAlarmModBlocks.LABEL_059FR);
	public static final RegistryObject<Item> LABEL_063 = block(MoreScpAlarmModBlocks.LABEL_063);
	public static final RegistryObject<Item> LABEL_079 = block(MoreScpAlarmModBlocks.LABEL_079);
	public static final RegistryObject<Item> LABEL_087 = block(MoreScpAlarmModBlocks.LABEL_087);
	public static final RegistryObject<Item> LABEL_096 = block(MoreScpAlarmModBlocks.LABEL_096);
	public static final RegistryObject<Item> LABEL_098 = block(MoreScpAlarmModBlocks.LABEL_098);
	public static final RegistryObject<Item> LABEL_1000 = block(MoreScpAlarmModBlocks.LABEL_1000);
	public static final RegistryObject<Item> LABEL_1025 = block(MoreScpAlarmModBlocks.LABEL_1025);
	public static final RegistryObject<Item> LABEL_1032_RU = block(MoreScpAlarmModBlocks.LABEL_1032_RU);
	public static final RegistryObject<Item> LABEL_1048 = block(MoreScpAlarmModBlocks.LABEL_1048);
	public static final RegistryObject<Item> LABEL_1057 = block(MoreScpAlarmModBlocks.LABEL_1057);
	public static final RegistryObject<Item> LABEL_106 = block(MoreScpAlarmModBlocks.LABEL_106);
	public static final RegistryObject<Item> LABEL_1074 = block(MoreScpAlarmModBlocks.LABEL_1074);
	public static final RegistryObject<Item> LABEL_109 = block(MoreScpAlarmModBlocks.LABEL_109);
	public static final RegistryObject<Item> LABEL_111 = block(MoreScpAlarmModBlocks.LABEL_111);
	public static final RegistryObject<Item> LABEL_1162 = block(MoreScpAlarmModBlocks.LABEL_1162);
	public static final RegistryObject<Item> LABEL_124 = block(MoreScpAlarmModBlocks.LABEL_124);
	public static final RegistryObject<Item> LABEL_131 = block(MoreScpAlarmModBlocks.LABEL_131);
	public static final RegistryObject<Item> LABEL_143FR = block(MoreScpAlarmModBlocks.LABEL_143FR);
	public static final RegistryObject<Item> LABEL_143 = block(MoreScpAlarmModBlocks.LABEL_143);

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
