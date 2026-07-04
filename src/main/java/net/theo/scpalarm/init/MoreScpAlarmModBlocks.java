
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.theo.scpalarm.init;

import net.theo.scpalarm.block.CassiesignBlock;
import net.theo.scpalarm.MoreScpAlarmMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

public class MoreScpAlarmModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreScpAlarmMod.MODID);
	public static final RegistryObject<Block> CASSIESIGN = REGISTRY.register("cassiesign", () -> new CassiesignBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
