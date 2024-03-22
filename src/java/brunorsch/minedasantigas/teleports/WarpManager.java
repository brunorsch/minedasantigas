package brunorsch.minedasantigas.teleports;

import static brunorsch.minedasantigas.utils.CollectionUtils.mapOf;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;
import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import brunorsch.minedasantigas.DasAntigas;

public class WarpManager {
    private static final String WARP_CONFIG_PATH = "Warps.";

    public static Set<String> list() {
        return ofNullable(DasAntigas.inst().getConfig().getConfigurationSection("Warps"))
            .map(sec -> sec.getKeys(false))
            .orElse(emptySet());
    }

    public static void set(String warpName, Location location) {
        DasAntigas.config()
            .set(WARP_CONFIG_PATH + warpName.toLowerCase(), mapOf(
                pair("world", location.getWorld().getName()),
                pair("x", location.getBlockX()),
                pair("y", location.getBlockY()),
                pair("z", location.getBlockZ()),
                pair("pitch", location.getPitch()),
                pair("yaw", location.getYaw())
            ));

        DasAntigas.configSave();
    }

    public static Optional<Location> getLocation(String warpName) {
        final Optional<ConfigurationSection> section = ofNullable(
            DasAntigas.inst().getConfig().getConfigurationSection(WARP_CONFIG_PATH + warpName.toLowerCase()));

        return section
            .map(safeSection -> new Location(
                Bukkit.getWorld(safeSection.getString("world")),
                safeSection.getDouble("x"),
                safeSection.getDouble("y"),
                safeSection.getDouble("z"),
                (float) safeSection.getDouble("yaw"),
                (float) safeSection.getDouble("pitch")));
    }

    public static void delete(String warpName) {
        DasAntigas.inst().getConfig().set(WARP_CONFIG_PATH + warpName, null);

        DasAntigas.inst().saveConfig();
    }
}