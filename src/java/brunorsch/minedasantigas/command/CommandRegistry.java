package brunorsch.minedasantigas.command;

import static brunorsch.minedasantigas.utils.CollectionUtils.setOf;
import static brunorsch.minedasantigas.utils.internals.CommandMapWrapper.withCommandMap;

import java.util.Set;

import org.bukkit.command.Command;

import brunorsch.minedasantigas.fly.FlyCommand;
import brunorsch.minedasantigas.home.HomeCommand;
import brunorsch.minedasantigas.teleports.DelwarpCommand;
import brunorsch.minedasantigas.teleports.SetwarpCommand;
import brunorsch.minedasantigas.teleports.TpCommand;
import brunorsch.minedasantigas.teleports.WarpCommand;

public class CommandRegistry {
    private final static Set<Command> commands = setOf(
        new DelwarpCommand(),
        new FlyCommand(),
        new HomeCommand(),
        new SetwarpCommand(),
        new TpCommand(),
        new WarpCommand()
    );

    public static void registerAll() {
        withCommandMap(commandMap ->
            commands.forEach(cmd -> commandMap.register(cmd.getName(), cmd))
        );
    }
}