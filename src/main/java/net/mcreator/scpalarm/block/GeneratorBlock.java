package net.mcreator.scpalarm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;



import javax.annotation.Nullable;

public class GeneratorBlock extends Block {

    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public GeneratorBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(net.minecraft.item.BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    public TileEntity createTileEntity(BlockState state, World world) {
        return new GeneratorTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
if (!world.isRemote) {
    TileEntity tile = world.getTileEntity(pos);
    if (tile instanceof GeneratorTileEntity) {
        GeneratorTileEntity genTile = (GeneratorTileEntity) tile;
        genTile.toggleTablet(player.getHeldItem(hand));
    }
    player.sendStatusMessage(new StringTextComponent("Bloc activé avec : " + player.getHeldItem(hand).getDisplayName().getString()), true);
    return ActionResultType.SUCCESS;
}
return ActionResultType.SUCCESS;

    }

    // --- TileEntity interne pour gérer la logique ---
    public static class GeneratorTileEntity extends TileEntity implements net.minecraft.tileentity.ITickableTileEntity {

        private static final int COUNTDOWN_START = 60 * 20; // 60 secondes * 20 ticks/sec
        private int countdown = -1;
        private boolean tabletInserted = false;

        @ObjectHolder("more_scp_alarm:generator_on_tileentity")
        public static TileEntityType<GeneratorTileEntity> TYPE;

        public GeneratorTileEntity() {
            super(TYPE);
        }

        public void tick() {
    if (world == null || world.isRemote) return;

    if (tabletInserted) {
        if (countdown == -1) {
            countdown = COUNTDOWN_START;
            playSound("more_scp_alarm:generator-on");
        } else if (countdown > 0) {
            if (countdown % 20 == 0) {
                playSound("more_scp_alarm:generator-count");
            }
            countdown--;
        } else if (countdown == 0) {
            playSound("more_scp_alarm:generator-done");
            countdown = -1;
        }
    }
}

        public void toggleTablet(ItemStack heldItem) {
            boolean hadTablet = tabletInserted;
            if (!heldItem.isEmpty() && heldItem.getItem().getRegistryName() != null
                    && heldItem.getItem().getRegistryName().toString().equals("more_scp_alarm:generatortablet")) {
                tabletInserted = true;
            } else {
                tabletInserted = false;
            }

            if (tabletInserted && !hadTablet) {
                playSound("more_scp_alarm:generator-on");
                countdown = COUNTDOWN_START;
            } else if (!tabletInserted && hadTablet) {
                playSound("more_scp_alarm:generator-off");
                countdown = -1;
            }
        }

        private void playSound(String soundName) {
    if (world != null && !world.isRemote) {
        SoundEvent soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundName));
        if (soundEvent != null) {
            world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }
}
    }
}
