
package net.theo.scpalarm.block;

import net.theo.scpalarm.procedures.BigdoorControllerProcedure;
import net.theo.scpalarm.init.MoreScpAlarmModBlocks;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public class NewdoorPartBlock extends Block {
	public enum DoorPart implements net.minecraft.util.StringRepresentable {
		LEFT_DOWN("left_down"),
		LEFT_UP("left_up"),
		RIGHT_UP("right_up"),
		RIGHT_MID("right_mid"),
		RIGHT_DOWN("right_down");

		private final String name;

		DoorPart(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}

	public static final EnumProperty<DoorPart> DOOR_PART = EnumProperty.create("door_part", DoorPart.class);
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	public NewdoorPartBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.EMPTY).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(DOOR_PART, DoorPart.LEFT_DOWN).setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DOOR_PART, FACING);
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
			case NORTH, SOUTH -> box(0, 0, 4, 16, 16, 12);
			case EAST, WEST -> box(4, 0, 0, 12, 16, 16);
			default -> box(0, 0, 4, 16, 16, 12);
		};
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		return new ItemStack(MoreScpAlarmModBlocks.NEWDOOR.get());
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
