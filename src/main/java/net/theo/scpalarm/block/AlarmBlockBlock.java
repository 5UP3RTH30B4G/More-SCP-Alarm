
package net.theo.scpalarm.block;

import net.theo.scpalarm.procedures.AlarmBlockOnBlockHitByProjectileProcedure;
import net.theo.scpalarm.procedures.AlarmBlockBlockDestroyedProcedure;
import net.theo.scpalarm.procedures.AlarmBlockBlockAddedProcedure;
import net.theo.scpalarm.block.entity.AlarmBlockBlockEntity;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Containers;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class AlarmBlockBlock extends Block implements EntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public AlarmBlockBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			default -> Shapes.or(box(3, 7, 3, 13, 13, 4), box(3, 6.1327, 2.83836, 13, 7.1327, 8.33836), box(7, 9, 4, 9, 11, 10), box(3, 7.1327, 2.73836, 4, 10.6327, 8.33836), box(3, 9, 4, 4, 12, 10), box(12, 9, 4, 13, 12, 10),
					box(3, 12, 4, 13, 13, 10), box(12, 7.1327, 2.73836, 13, 10.6327, 8.33836), box(7, 4, 2, 9, 6, 7));
			case NORTH -> Shapes.or(box(3, 7, 12, 13, 13, 13), box(3, 6.1327, 7.66164, 13, 7.1327, 13.16164), box(7, 9, 6, 9, 11, 12), box(12, 7.1327, 7.66164, 13, 10.6327, 13.26164), box(12, 9, 6, 13, 12, 12), box(3, 9, 6, 4, 12, 12),
					box(3, 12, 6, 13, 13, 12), box(3, 7.1327, 7.66164, 4, 10.6327, 13.26164), box(7, 4, 9, 9, 6, 14));
			case EAST -> Shapes.or(box(3, 7, 3, 4, 13, 13), box(2.83836, 6.1327, 3, 8.33836, 7.1327, 13), box(4, 9, 7, 10, 11, 9), box(2.73836, 7.1327, 12, 8.33836, 10.6327, 13), box(4, 9, 12, 10, 12, 13), box(4, 9, 3, 10, 12, 4),
					box(4, 12, 3, 10, 13, 13), box(2.73836, 7.1327, 3, 8.33836, 10.6327, 4), box(2, 4, 7, 7, 6, 9));
			case WEST -> Shapes.or(box(12, 7, 3, 13, 13, 13), box(7.66164, 6.1327, 3, 13.16164, 7.1327, 13), box(6, 9, 7, 12, 11, 9), box(7.66164, 7.1327, 3, 13.26164, 10.6327, 4), box(6, 9, 3, 12, 12, 4), box(6, 9, 12, 12, 12, 13),
					box(6, 12, 3, 12, 13, 13), box(7.66164, 7.1327, 12, 13.26164, 10.6327, 13), box(9, 4, 7, 14, 6, 9));
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if (context.getClickedFace().getAxis() == Direction.Axis.Y)
			return super.getStateForPlacement(context).setValue(FACING, Direction.NORTH);
		return super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		AlarmBlockBlockAddedProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
		AlarmBlockBlockDestroyedProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
		return retval;
	}

	@Override
	public void wasExploded(Level world, BlockPos pos, Explosion e) {
		super.wasExploded(world, pos, e);
		AlarmBlockBlockDestroyedProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void onProjectileHit(Level world, BlockState blockstate, BlockHitResult hit, Projectile entity) {
		AlarmBlockOnBlockHitByProjectileProcedure.execute(world, hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ());
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AlarmBlockBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof AlarmBlockBlockEntity be) {
				Containers.dropContents(world, pos, be);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof AlarmBlockBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}
