
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.scpalarm.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.scpalarm.item.LogoItem;
import net.mcreator.scpalarm.MoreScpAlarmMod;

public class MoreScpAlarmModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MoreScpAlarmMod.MODID);
	public static final RegistryObject<Item> GAZ = block(MoreScpAlarmModBlocks.GAZ);
	public static final RegistryObject<Item> LOGO = REGISTRY.register("logo", () -> new LogoItem());
	public static final RegistryObject<Item> GATE_1 = block(MoreScpAlarmModBlocks.GATE_1);
	public static final RegistryObject<Item> GATE_2 = block(MoreScpAlarmModBlocks.GATE_2);
	public static final RegistryObject<Item> GATE_3 = block(MoreScpAlarmModBlocks.GATE_3);
	public static final RegistryObject<Item> GATE_4 = block(MoreScpAlarmModBlocks.GATE_4);
	public static final RegistryObject<Item> DOOR_5 = block(MoreScpAlarmModBlocks.DOOR_5);
	public static final RegistryObject<Item> DOOR_6 = block(MoreScpAlarmModBlocks.DOOR_6);
	public static final RegistryObject<Item> DOOR_7 = block(MoreScpAlarmModBlocks.DOOR_7);
	public static final RegistryObject<Item> DOOR_8 = block(MoreScpAlarmModBlocks.DOOR_8);
	public static final RegistryObject<Item> DOOR_FRAME = block(MoreScpAlarmModBlocks.DOOR_FRAME);
	public static final RegistryObject<Item> ALARM = block(MoreScpAlarmModBlocks.ALARM);
	public static final RegistryObject<Item> ALARMSENDER = block(MoreScpAlarmModBlocks.ALARMSENDER);
	public static final RegistryObject<Item> ALARMVA = block(MoreScpAlarmModBlocks.ALARMVA);
	public static final RegistryObject<Item> TVCHECKPOINT = block(MoreScpAlarmModBlocks.TVCHECKPOINT);
	public static final RegistryObject<Item> TVOFF = block(MoreScpAlarmModBlocks.TVOFF);
	public static final RegistryObject<Item> TVLIGHT = block(MoreScpAlarmModBlocks.TVLIGHT);
	public static final RegistryObject<Item> LIGHTOFF = block(MoreScpAlarmModBlocks.LIGHTOFF);
	public static final RegistryObject<Item> TRIG_079 = block(MoreScpAlarmModBlocks.TRIG_079);
	public static final RegistryObject<Item> TV_079 = block(MoreScpAlarmModBlocks.TV_079);
	public static final RegistryObject<Item> ALARMVA_2 = block(MoreScpAlarmModBlocks.ALARMVA_2);
	public static final RegistryObject<Item> ALARMVA_3 = block(MoreScpAlarmModBlocks.ALARMVA_3);
	public static final RegistryObject<Item> TV_895 = block(MoreScpAlarmModBlocks.TV_895);
	public static final RegistryObject<Item> TV_895OFF = block(MoreScpAlarmModBlocks.TV_895OFF);
	public static final RegistryObject<Item> NEWDOOR = block(MoreScpAlarmModBlocks.NEWDOOR);

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
