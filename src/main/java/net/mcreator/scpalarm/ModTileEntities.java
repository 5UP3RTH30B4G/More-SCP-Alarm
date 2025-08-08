package net.mcreator.scpalarm;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.tileentity.TileEntityType;
import net.mcreator.scpalarm.block.GeneratorBlock;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "more_scp_alarm");

    public static final RegistryObject<TileEntityType<GeneratorBlock.GeneratorTileEntity>> GENERATOR_TILE_ENTITY =
        TILE_ENTITIES.register("generator_on_tileentity",
            () -> TileEntityType.Builder.create(GeneratorBlock.GeneratorTileEntity::new, ModBlocks.GENERATOR_BLOCK.get()).build(null));
}
