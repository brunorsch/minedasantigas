package brunorsch.minedasantigas.teleports;

import static brunorsch.minedasantigas.utils.CollectionUtils.mapOf;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;
import static java.util.Optional.ofNullable;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import brunorsch.minedasantigas.DasAntigas;

public class ConfigLocationHelper {
    public static void set(String path, Location location) {
        DasAntigas.config()
            .set(path, mapOf(
                pair("world", location.getWorld().getName()),
                pair("x", location.getBlockX()),
                pair("y", location.getBlockY()),
                pair("z", location.getBlockZ()),
                pair("pitch", location.getPitch()),
                pair("yaw", location.getYaw())
            ));

        DasAntigas.configSave();
    }

    public static Optional<Location> get(String path) {
        final Optional<ConfigurationSection> section = ofNullable(
            DasAntigas.inst().getConfig().getConfigurationSection(path));

        return section
            .map(safeSection -> new Location(
                Bukkit.getWorld(safeSection.getString("world")),
                safeSection.getDouble("x"),
                safeSection.getDouble("y"),
                safeSection.getDouble("z"),
                (float) safeSection.getDouble("yaw"),
                (float) safeSection.getDouble("pitch")));
    }
}