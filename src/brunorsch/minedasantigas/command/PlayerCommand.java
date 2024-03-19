package brunorsch.minedasantigas.command;

import static java.util.Collections.emptyList;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends Command {
    public PlayerCommand(final String name, final String description, final List<String> aliases) {
        super(name, description, "/" + name, aliases);
    }

    public PlayerCommand(final String name, final String description) {
        this(name, description, emptyList());
    }

    @Override
    public boolean execute(final CommandSender sender, final String s, final String[] args) {
        if(sender instanceof Player) {
            onCommand((Player) sender, args);
        } else {
            sender.sendMessage("Comando disponível apenas no console");
        }

        return true;
    }

    public abstract void onCommand(final Player player, final String[] args);
}