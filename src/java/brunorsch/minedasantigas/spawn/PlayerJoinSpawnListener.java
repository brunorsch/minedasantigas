package brunorsch.minedasantigas.spawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import brunorsch.minedasantigas.teleports.ConfigLocationHelper;
import lombok.val;

public class PlayerJoinSpawnListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        val player = event.getPlayer();

        if(!player.hasPlayedBefore()) {
            ConfigLocationHelper.get("Spawn")
                .ifPresent(player::teleport);
        }
    }
}