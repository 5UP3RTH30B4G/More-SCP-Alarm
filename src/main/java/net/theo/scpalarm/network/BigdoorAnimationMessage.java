package net.theo.scpalarm.network;

import java.util.function.Supplier;

import net.theo.scpalarm.client.BigdoorAnimationRenderer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public record BigdoorAnimationMessage(BlockPos base, Direction facing, int fromSlide, int targetSlide, int duration) {
    public static void encode(BigdoorAnimationMessage message, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(message.base);
        buffer.writeEnum(message.facing);
        buffer.writeInt(message.fromSlide);
        buffer.writeInt(message.targetSlide);
        buffer.writeInt(message.duration);
    }

    public static BigdoorAnimationMessage decode(FriendlyByteBuf buffer) {
        return new BigdoorAnimationMessage(buffer.readBlockPos(), buffer.readEnum(Direction.class), buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    public static void handle(BigdoorAnimationMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> BigdoorAnimationRenderer.start(message));
        context.setPacketHandled(true);
    }
}
