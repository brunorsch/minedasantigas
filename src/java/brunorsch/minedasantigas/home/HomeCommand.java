package brunorsch.minedasantigas.home;

import static java.util.Collections.singletonList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import brunorsch.minedasantigas.command.PlayerCommand;

public class HomeCommand extends PlayerCommand {
    public HomeCommand() {
        super("home", "Teleporte para sua casa", singletonList("h"));
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        player.teleport(player.getBedSpawnLocation(), TeleportCause.COMMAND);
        player.sendMessage("§e[§6§l!§r§e] Teleportado para sua cama!");
    }
}