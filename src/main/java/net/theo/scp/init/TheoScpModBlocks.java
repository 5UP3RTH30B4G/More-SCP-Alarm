
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.theo.scp.init;

import net.theo.scp.block.CassiesignBlock;
import net.theo.scp.TheoScpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

public class TheoScpModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, TheoScpMod.MODID);
	public static final RegistryObject<Block> CASSIESIGN = REGISTRY.register("cassiesign", () -> new CassiesignBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
