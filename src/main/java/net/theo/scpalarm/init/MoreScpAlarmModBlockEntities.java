
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.theo.scpalarm.init;

import net.theo.scpalarm.block.entity.TVOFFBlockEntity;
import net.theo.scpalarm.block.entity.TVCheckpointLightBlockEntity;
import net.theo.scpalarm.block.entity.TVCheckpointBlockEntity;
import net.theo.scpalarm.block.entity.TV895BlockEntity;
import net.theo.scpalarm.block.entity.TV079BlockEntity;
import net.theo.scpalarm.block.entity.AlarmvaBlockEntity;
import net.theo.scpalarm.block.entity.Alarmva3BlockEntity;
import net.theo.scpalarm.block.entity.Alarmva2BlockEntity;
import net.theo.scpalarm.block.entity.AlarmsirenBlockEntity;
import net.theo.scpalarm.MoreScpAlarmMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

public class MoreScpAlarmModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MoreScpAlarmMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> ALARMVA = register("alarmva", MoreScpAlarmModBlocks.ALARMVA, AlarmvaBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> ALARMVA_2 = register("alarmva_2", MoreScpAlarmModBlocks.ALARMVA_2, Alarmva2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> ALARMVA_3 = register("alarmva_3", MoreScpAlarmModBlocks.ALARMVA_3, Alarmva3BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> ALARMSIREN = register("alarmsiren", MoreScpAlarmModBlocks.ALARMSIREN, AlarmsirenBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> TVOFF = register("tvoff", MoreScpAlarmModBlocks.TVOFF, TVOFFBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> TV_CHECKPOINT = register("tv_checkpoint", MoreScpAlarmModBlocks.TV_CHECKPOINT, TVCheckpointBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> TV_CHECKPOINT_LIGHT = register("tv_checkpoint_light", MoreScpAlarmModBlocks.TV_CHECKPOINT_LIGHT, TVCheckpointLightBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> TV_079 = register("tv_079", MoreScpAlarmModBlocks.TV_079, TV079BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> TV_895 = register("tv_895", MoreScpAlarmModBlocks.TV_895, TV895BlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
