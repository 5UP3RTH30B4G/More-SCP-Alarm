
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.scpalarm.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import net.mcreator.scpalarm.block.entity.AlarmvaBlockEntity;
import net.mcreator.scpalarm.block.entity.Alarmva3BlockEntity;
import net.mcreator.scpalarm.block.entity.Alarmva2BlockEntity;
import net.mcreator.scpalarm.block.entity.AlarmBlockEntity;
import net.mcreator.scpalarm.MoreScpAlarmMod;

public class MoreScpAlarmModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MoreScpAlarmMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> ALARM = register("alarm", MoreScpAlarmModBlocks.ALARM, AlarmBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> ALARMVA = register("alarmva", MoreScpAlarmModBlocks.ALARMVA, AlarmvaBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> ALARMVA_2 = register("alarmva_2", MoreScpAlarmModBlocks.ALARMVA_2, Alarmva2BlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> ALARMVA_3 = register("alarmva_3", MoreScpAlarmModBlocks.ALARMVA_3, Alarmva3BlockEntity::new);

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
