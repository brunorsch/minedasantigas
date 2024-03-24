package brunorsch.minedasantigas.placas;

import static org.bukkit.event.EventPriority.HIGHEST;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import lombok.val;

public class CustomizacaoPlacasListener implements Listener {
    @EventHandler(priority = HIGHEST)
    public void onSignEdit(SignChangeEvent e) {
        if(e.isCancelled()) return;

        for (int i = 0; i < e.getLines().length; i++) {
            val line = e.getLine(i);

            e.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
        }
    }
}