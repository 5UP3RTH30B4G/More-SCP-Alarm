
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.scpalarm.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.mcreator.scpalarm.block.TvoffBlock;
import net.mcreator.scpalarm.block.TvlightBlock;
import net.mcreator.scpalarm.block.TvcheckpointBlock;
import net.mcreator.scpalarm.block.Tv895offBlock;
import net.mcreator.scpalarm.block.Tv895Block;
import net.mcreator.scpalarm.block.Tv079Block;
import net.mcreator.scpalarm.block.Trig079Block;
import net.mcreator.scpalarm.block.NewdoorBlock;
import net.mcreator.scpalarm.block.LightoffBlock;
import net.mcreator.scpalarm.block.GazBlock;
import net.mcreator.scpalarm.block.Gate4Block;
import net.mcreator.scpalarm.block.Gate3Block;
import net.mcreator.scpalarm.block.Gate2Block;
import net.mcreator.scpalarm.block.Gate1Block;
import net.mcreator.scpalarm.block.DoorFrameBlock;
import net.mcreator.scpalarm.block.Door8Block;
import net.mcreator.scpalarm.block.Door7Block;
import net.mcreator.scpalarm.block.Door6Block;
import net.mcreator.scpalarm.block.Door5Block;
import net.mcreator.scpalarm.block.AlarmvaBlock;
import net.mcreator.scpalarm.block.Alarmva3Block;
import net.mcreator.scpalarm.block.Alarmva2Block;
import net.mcreator.scpalarm.block.AlarmsenderBlock;
import net.mcreator.scpalarm.block.AlarmBlock;
import net.mcreator.scpalarm.MoreScpAlarmMod;

public class MoreScpAlarmModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreScpAlarmMod.MODID);
	public static final RegistryObject<Block> GAZ = REGISTRY.register("gaz", () -> new GazBlock());
	public static final RegistryObject<Block> GATE_1 = REGISTRY.register("gate_1", () -> new Gate1Block());
	public static final RegistryObject<Block> GATE_2 = REGISTRY.register("gate_2", () -> new Gate2Block());
	public static final RegistryObject<Block> GATE_3 = REGISTRY.register("gate_3", () -> new Gate3Block());
	public static final RegistryObject<Block> GATE_4 = REGISTRY.register("gate_4", () -> new Gate4Block());
	public static final RegistryObject<Block> DOOR_5 = REGISTRY.register("door_5", () -> new Door5Block());
	public static final RegistryObject<Block> DOOR_6 = REGISTRY.register("door_6", () -> new Door6Block());
	public static final RegistryObject<Block> DOOR_7 = REGISTRY.register("door_7", () -> new Door7Block());
	public static final RegistryObject<Block> DOOR_8 = REGISTRY.register("door_8", () -> new Door8Block());
	public static final RegistryObject<Block> DOOR_FRAME = REGISTRY.register("door_frame", () -> new DoorFrameBlock());
	public static final RegistryObject<Block> ALARM = REGISTRY.register("alarm", () -> new AlarmBlock());
	public static final RegistryObject<Block> ALARMSENDER = REGISTRY.register("alarmsender", () -> new AlarmsenderBlock());
	public static final RegistryObject<Block> ALARMVA = REGISTRY.register("alarmva", () -> new AlarmvaBlock());
	public static final RegistryObject<Block> TVCHECKPOINT = REGISTRY.register("tvcheckpoint", () -> new TvcheckpointBlock());
	public static final RegistryObject<Block> TVOFF = REGISTRY.register("tvoff", () -> new TvoffBlock());
	public static final RegistryObject<Block> TVLIGHT = REGISTRY.register("tvlight", () -> new TvlightBlock());
	public static final RegistryObject<Block> LIGHTOFF = REGISTRY.register("lightoff", () -> new LightoffBlock());
	public static final RegistryObject<Block> TRIG_079 = REGISTRY.register("trig_079", () -> new Trig079Block());
	public static final RegistryObject<Block> TV_079 = REGISTRY.register("tv_079", () -> new Tv079Block());
	public static final RegistryObject<Block> ALARMVA_2 = REGISTRY.register("alarmva_2", () -> new Alarmva2Block());
	public static final RegistryObject<Block> ALARMVA_3 = REGISTRY.register("alarmva_3", () -> new Alarmva3Block());
	public static final RegistryObject<Block> TV_895 = REGISTRY.register("tv_895", () -> new Tv895Block());
	public static final RegistryObject<Block> TV_895OFF = REGISTRY.register("tv_895off", () -> new Tv895offBlock());
	public static final RegistryObject<Block> NEWDOOR = REGISTRY.register("newdoor", () -> new NewdoorBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
