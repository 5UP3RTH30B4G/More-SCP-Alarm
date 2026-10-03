
package net.theo.scpalarm.command;

import org.checkerframework.checker.units.qual.s;

import net.theo.scpalarm.network.MoreScpAlarmModVariables;
import net.theo.scpalarm.procedures.DebugOnProcedure;
import net.theo.scpalarm.procedures.DebugOffProcedure;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.util.FakePlayerFactory;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

@Mod.EventBusSubscriber
public class MsaCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("msa").requires(s -> s.hasPermission(4))
				.then(Commands.literal("debug").then(Commands.literal("on").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					double x = arguments.getSource().getPosition().x();
					double y = arguments.getSource().getPosition().y();
					double z = arguments.getSource().getPosition().z();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					Direction direction = Direction.DOWN;
					if (entity != null)
						direction = entity.getDirection();

					DebugOnProcedure.execute(entity);
					return 0;
				})).then(Commands.literal("off").executes(arguments -> {
					Level world = arguments.getSource().getUnsidedLevel();
					double x = arguments.getSource().getPosition().x();
					double y = arguments.getSource().getPosition().y();
					double z = arguments.getSource().getPosition().z();
					Entity entity = arguments.getSource().getEntity();
					if (entity == null && world instanceof ServerLevel _servLevel)
						entity = FakePlayerFactory.getMinecraft(_servLevel);
					Direction direction = Direction.DOWN;
					if (entity != null)
						direction = entity.getDirection();

					DebugOffProcedure.execute(entity);
					return 0;
				})))
				.then(Commands.literal("alarmpos")
						.then(Commands.literal("get").executes(arguments -> {
							String alarmPos = MoreScpAlarmModVariables.MapVariables
									.get(arguments.getSource().getLevel()).AlarmPos;
							String[] positions = alarmPos == null || alarmPos.isEmpty() || alarmPos.equals("\"\"")
									? new String[0]
									: alarmPos.split(";");
							String display = positions.length == 0 ? "vide" : String.join(" | ", positions);
							arguments.getSource().sendSuccess(
									() -> Component.literal("AlarmPos (" + positions.length + "): " + display), false);
							return 1;
						}))
						.then(Commands.literal("reset").executes(arguments -> {
							MoreScpAlarmModVariables.MapVariables mapVariables = MoreScpAlarmModVariables.MapVariables
									.get(arguments.getSource().getLevel());
							mapVariables.AlarmPos = "\"\"";
							mapVariables.syncData(arguments.getSource().getLevel());
							arguments.getSource().sendSuccess(() -> Component.literal("AlarmPos cleared."), false);
							return 1;
						}))))

		;
	}
}
