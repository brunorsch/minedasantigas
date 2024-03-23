package brunorsch.minedasantigas.registry;

import static brunorsch.minedasantigas.utils.CollectionUtils.setOf;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import brunorsch.minedasantigas.DasAntigas;
import brunorsch.minedasantigas.mecanicas.SpawnerPickListener;
import brunorsch.minedasantigas.spawn.PlayerJoinSpawnListener;

public class ListenerRegistry {
    private final static Set<Listener> listeners = setOf(
        new PlayerJoinSpawnListener(),
        new SpawnerPickListener()
    );

    public static void registerAll(DasAntigas plugin) {
        listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, plugin));
    }
}