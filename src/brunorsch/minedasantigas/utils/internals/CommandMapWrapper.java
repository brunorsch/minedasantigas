package brunorsch.minedasantigas.utils.internals;

import static brunorsch.minedasantigas.DasAntigas.log;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

public class CommandMapWrapper {
    public static void withCommandMap(Consumer<CommandMap> logic) {
        try {
            Field f = Bukkit.getServer().getClass()
                .getDeclaredField("commandMap");

            if(!f.isAccessible()) {
                f.setAccessible(true);
            }

            logic.accept((CommandMap) f.get(Bukkit.getServer()));
        } catch (Exception ex) {
            log().log(Level.SEVERE, "Erro inesperado ao utilizar o CommandMap do Bukkit", ex);
            throw new RuntimeException(ex);
        }
    }
}