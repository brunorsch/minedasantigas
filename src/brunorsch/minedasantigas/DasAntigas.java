package brunorsch.minedasantigas;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import brunorsch.minedasantigas.command.CommandRegistry;

public class DasAntigas extends JavaPlugin {
    private static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();

        CommandRegistry.registerAll();

        getLogger().info("Plugin inicializado");
        getLogger().info("By Bruno Preguiça");
    }

    public static Logger log() {
        requireNonNull(logger, "Use DasAntigas.log() apenas após a inicialização do plugin");
        return logger;
    }
}