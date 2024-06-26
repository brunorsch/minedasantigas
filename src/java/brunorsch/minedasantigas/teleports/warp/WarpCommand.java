package brunorsch.minedasantigas.teleports.warp;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.Mensagem.TELEPORTADO_COM_SUCESSO;
import static brunorsch.minedasantigas.locale.Mensagem.USO_CORRETO_WARP;
import static brunorsch.minedasantigas.locale.Mensagem.WARP_NAO_ENCONTRADO;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;
import static java.util.Collections.singletonList;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.teleports.WarpManager;

public class WarpCommand extends PlayerCommand {
    public WarpCommand() {
        super("warp", "Teleporta para um local público", singletonList("w"));
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(args.length != 1) {
            final Set<String> warps = WarpManager.list();

            String warpsFormatados = warps.isEmpty() ? "Nenhum :(" : StringUtils.join(warps, ", ");

            player.sendMessage(msg(USO_CORRETO_WARP, pair("warps", warpsFormatados)));
            return;
        }

        final Optional<Location> location = WarpManager.getLocation(args[0]);

        if(location.isPresent()) {
            player.teleport(location.get());
            player.sendMessage(msg(TELEPORTADO_COM_SUCESSO));
        } else {
            player.sendMessage(msg(WARP_NAO_ENCONTRADO));
        }
    }
}