package brunorsch.minedasantigas.teleports;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.Mensagem.USO_CORRETO;
import static brunorsch.minedasantigas.locale.Mensagem.WARP_NAO_ENCONTRADO;
import static brunorsch.minedasantigas.locale.Mensagem.WARP_TELEPORTADO;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;
import static java.util.Collections.singletonList;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;

public class WarpCommand extends PlayerCommand {
    public WarpCommand() {
        super("warp", "Teleporta para um local público", singletonList("w"));
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(args.length != 1) {
            player.sendMessage(msg(USO_CORRETO, pair("comando", "/warp <Nome>")));
            return;
        }

        final Optional<Location> location = WarpManager.getLocation(args[0]);

        if(location.isPresent()) {
            player.teleport(location.get());
            player.sendMessage(msg(WARP_TELEPORTADO));
        } else {
            player.sendMessage(msg(WARP_NAO_ENCONTRADO));
        }
    }
}