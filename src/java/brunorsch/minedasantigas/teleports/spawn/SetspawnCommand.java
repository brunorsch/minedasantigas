package brunorsch.minedasantigas.teleports.spawn;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.Mensagem.SEM_PERMISSAO;
import static brunorsch.minedasantigas.locale.Mensagem.SPAWN_SETADO;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.teleports.ConfigLocationHelper;

public class SetspawnCommand extends PlayerCommand {

    public SetspawnCommand() {
        super("setspawn", "Define o spawn do servidor");
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(!player.hasPermission(new Permission("minedasantigas.setspawn"))) {
            player.sendMessage(msg(SEM_PERMISSAO));
            return;
        }

        final Location playerLoc = player.getLocation();

        ConfigLocationHelper.set("Spawn", playerLoc);

        final World world = playerLoc.getWorld();

        world.setSpawnFlags(false, true);
        world.setSpawnLocation(playerLoc.getBlockX(), playerLoc.getBlockY(), playerLoc.getBlockZ());

        player.sendMessage(msg(SPAWN_SETADO));
    }
}