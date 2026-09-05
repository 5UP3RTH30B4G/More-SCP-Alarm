
package net.theo.scpalarm.block;

import net.theo.scpalarm.procedures.BigdoorPlacePartProcedure;
import net.theo.scpalarm.procedures.BigdoorControllerProcedure;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.common.util.ForgeSoundType;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class NewdoorBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty ANIMATING = BooleanProperty.create("animating");

	public NewdoorBlock() {
		super(BlockBehaviour.Properties.of()
				.sound(new ForgeSoundType(1.0f, 1.0f, () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("more_scp_alarm:door-break")), () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.step")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")), () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.hit")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.fall"))))
				.strength(1f, 10f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ANIMATING, false));
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
			default -> box(0, 0, 4, 16, 16, 12);
			case NORTH -> box(0, 0, 4, 16, 16, 12);
			case EAST -> box(4, 0, 0, 12, 16, 16);
			case WEST -> box(4, 0, 0, 12, 16, 16);
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, ANIMATING);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return state.getValue(ANIMATING) ? RenderShape.INVISIBLE : RenderShape.MODEL;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
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
		BigdoorPlacePartProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate.getValue(FACING));
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, fromPos, moving);
		if (!world.isClientSide() && world instanceof net.minecraft.server.level.ServerLevel serverLevel) {
			BigdoorControllerProcedure.neighborChanged(serverLevel, pos);
		}
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!world.isClientSide()) {
			BigdoorControllerProcedure.onRemoved((net.minecraft.server.level.ServerLevel) world, pos, newState);
		}
		super.onRemove(state, world, pos, newState, isMoving);
	}
}
