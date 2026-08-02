package net.theo.scpalarm.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class PanellinkProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(("Here the wiki's link: " + "&b&n&lhttps://5up3r.gitbook.io/more-scp-alarm-wiki/support/checking-the-in-game-list")), false);
	}
}
