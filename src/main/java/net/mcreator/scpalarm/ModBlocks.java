package net.mcreator.scpalarm;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.mcreator.scpalarm.block.GeneratorBlock;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "more_scp_alarm");

    public static final RegistryObject<Block> GENERATOR_BLOCK = BLOCKS.register("generator_on",
        () -> new GeneratorBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3f)));
}
