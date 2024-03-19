package brunorsch.minedasantigas.command;

import static brunorsch.minedasantigas.utils.CollectionUtils.setOf;
import static brunorsch.minedasantigas.utils.internals.CommandMapWrapper.withCommandMap;

import java.util.Set;

import org.bukkit.command.Command;

import brunorsch.minedasantigas.fly.FlyCommand;

public class CommandRegistry {
    private final static Set<Command> commands = setOf(
        new FlyCommand()
    );

    public static void registerAll() {
        withCommandMap(commandMap ->
            commands.forEach(cmd -> commandMap.register(cmd.getName(), cmd))
        );
    }
}