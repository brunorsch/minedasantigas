package brunorsch.minedasantigas.teleports;

import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.Set;

import org.bukkit.Location;

import brunorsch.minedasantigas.DasAntigas;

public class WarpManager {
    private static final String WARP_CONFIG_PATH = "Warps.";

    public static Set<String> list() {
        return ofNullable(DasAntigas.inst().getConfig().getConfigurationSection("Warps"))
            .map(sec -> sec.getKeys(false))
            .orElse(emptySet());
    }

    public static void set(String warpName, Location location) {
        ConfigLocationHelper.set(WARP_CONFIG_PATH + warpName.toLowerCase(), location);
    }

    public static Optional<Location> getLocation(String warpName) {
        return ConfigLocationHelper.get(WARP_CONFIG_PATH + warpName.toLowerCase());
    }

    public static void delete(String warpName) {
        DasAntigas.inst().getConfig().set(WARP_CONFIG_PATH + warpName, null);

        DasAntigas.inst().saveConfig();
    }
}