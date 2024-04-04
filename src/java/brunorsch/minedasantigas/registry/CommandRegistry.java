package brunorsch.minedasantigas.registry;

import static brunorsch.minedasantigas.utils.CollectionUtils.setOf;
import static brunorsch.minedasantigas.utils.internals.CommandMapWrapper.withCommandMap;

import java.util.Set;

import org.bukkit.command.Command;

import brunorsch.minedasantigas.fly.FlyCommand;
import brunorsch.minedasantigas.locations.home.HomeCommand;
import brunorsch.minedasantigas.locations.home.SethomeCommand;
import brunorsch.minedasantigas.locations.loja.LojaCommand;
import brunorsch.minedasantigas.locations.loja.SetlojaCommand;
import brunorsch.minedasantigas.locations.pwarp.PwarpCommand;
import brunorsch.minedasantigas.locations.pwarp.SetpwarpCommand;
import brunorsch.minedasantigas.spawn.SetspawnCommand;
import brunorsch.minedasantigas.spawn.SpawnCommand;
import brunorsch.minedasantigas.teleports.warp.DelwarpCommand;
import brunorsch.minedasantigas.teleports.warp.SetwarpCommand;
import brunorsch.minedasantigas.teleports.TpCommand;
import brunorsch.minedasantigas.teleports.warp.WarpCommand;

public class CommandRegistry {
    private final static Set<Command> commands = setOf(
        new DelwarpCommand(),
        new FlyCommand(),
        new HomeCommand(),
        new LojaCommand(),
        new PwarpCommand(),
        new SethomeCommand(),
        new SetlojaCommand(),
        new SetpwarpCommand(),
        new SetwarpCommand(),
        new SetspawnCommand(),
        new SpawnCommand(),
        new TpCommand(),
        new WarpCommand()
    );

    public static void registerAll() {
        withCommandMap(commandMap ->
            commands.forEach(cmd -> commandMap.register(cmd.getName(), cmd))
        );
    }
}