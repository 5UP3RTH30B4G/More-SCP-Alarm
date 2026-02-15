
package net.mcreator.scpalarm.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.scpalarm.world.inventory.AvarselectorMenu;
import net.mcreator.scpalarm.procedures.C3Procedure;
import net.mcreator.scpalarm.procedures.C2Procedure;
import net.mcreator.scpalarm.procedures.C1Procedure;
import net.mcreator.scpalarm.procedures.AvarsoundidProcedure;
import net.mcreator.scpalarm.MoreScpAlarmMod;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AvarselectorButtonMessage {
	private final int buttonID, x, y, z;

	public AvarselectorButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public AvarselectorButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(AvarselectorButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(AvarselectorButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
		HashMap guistate = AvarselectorMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			AvarsoundidProcedure.execute(world, entity, guistate);
		}
		if (buttonID == 1) {

			C1Procedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 2) {

			C2Procedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 3) {

			C3Procedure.execute(world, x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		MoreScpAlarmMod.addNetworkMessage(AvarselectorButtonMessage.class, AvarselectorButtonMessage::buffer, AvarselectorButtonMessage::new, AvarselectorButtonMessage::handler);
	}
}
