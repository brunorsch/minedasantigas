package brunorsch.minedasantigas;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import brunorsch.minedasantigas.data.DataManager;
import brunorsch.minedasantigas.registry.CommandRegistry;
import brunorsch.minedasantigas.registry.ListenerRegistry;

public class DasAntigas extends JavaPlugin {
    private static DasAntigas instance;
    private static Logger logger;
    private static YamlConfiguration config;
    private static File configFile;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();

        configFile = new File(getDataFolder().getPath(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        DataManager.setup();
        CommandRegistry.registerAll();
        ListenerRegistry.registerAll(this);

        getLogger().info("Plugin inicializado");
        getLogger().info("By Bruno Preguiça");
    }

    @Override
    public void onDisable() {
        configSave();
    }

    public static DasAntigas inst() {
        return instance;
    }

    public static Logger log() {
        requireNonNull(logger, "Use DasAntigas.log() apenas após a inicialização do plugin");
        return logger;
    }

    public static YamlConfiguration config() {
        return config;
    }

    public static void configSave() {
        try {
            config().save(configFile);
        } catch (IOException e) {
            log().log(Level.SEVERE, "Erro ao salvar a config", e);
        }
    }
}