
package net.theo.scpalarm.network;

import net.theo.scpalarm.world.inventory.ASelectorMenu;
import net.theo.scpalarm.procedures.PlusProcedure;
import net.theo.scpalarm.procedures.MinusProcedure;
import net.theo.scpalarm.procedures.AvargonProcedure;
import net.theo.scpalarm.procedures.AsoundidProcedure;
import net.theo.scpalarm.MoreScpAlarmMod;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ASelectorButtonMessage {
	private final int buttonID, x, y, z;

	public ASelectorButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public ASelectorButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(ASelectorButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(ASelectorButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Player entity = context.getSender();
			int buttonID = message.buttonID;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			handleButtonAction(entity, buttonID, x, y, z);
		});
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = ASelectorMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			AsoundidProcedure.execute(world, x, y, z, entity, guistate);
		}
		if (buttonID == 1) {

			AvargonProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 2) {

			PlusProcedure.execute(world, x, y, z);
		}
		if (buttonID == 3) {

			MinusProcedure.execute(world, x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		MoreScpAlarmMod.addNetworkMessage(ASelectorButtonMessage.class, ASelectorButtonMessage::buffer, ASelectorButtonMessage::new, ASelectorButtonMessage::handler);
	}
}
