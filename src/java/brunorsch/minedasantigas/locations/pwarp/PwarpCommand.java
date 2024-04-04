package brunorsch.minedasantigas.locations.pwarp;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.LocaleProvider.usoCorreto;
import static brunorsch.minedasantigas.locale.Mensagem.PWARP_DICA;
import static brunorsch.minedasantigas.locale.Mensagem.PWARP_DICA2;
import static brunorsch.minedasantigas.locale.Mensagem.PWARP_DICA3;
import static brunorsch.minedasantigas.locale.Mensagem.TELEPORTADO_COM_SUCESSO;
import static brunorsch.minedasantigas.locale.Mensagem.USO_CORRETO_WARP;
import static brunorsch.minedasantigas.locale.Mensagem.WARP_NAO_ENCONTRADO;
import static brunorsch.minedasantigas.locations.TipoLoc.PWARP;
import static brunorsch.minedasantigas.utils.CollectionUtils.mapOf;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;
import static java.util.Collections.singletonList;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.locations.LocRepository;
import brunorsch.minedasantigas.utils.ResultRunnable;
import lombok.val;

public class PwarpCommand extends PlayerCommand {
    public PwarpCommand() {
        super("pwarp", "Teleporta para um warp de player", singletonList("pw"));
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(args.length == 0 || args.length > 2) {
            player.sendMessage(usoCorreto("/pwarp [Player] <Nome do Warp>"));
            player.sendMessage(msg(PWARP_DICA));
            player.sendMessage(msg(PWARP_DICA2, "player", player.getName()));
            player.sendMessage(msg(PWARP_DICA3));
            return;
        }

        val nomePlayer = ((args.length == 2) ? args[0] : player.getName()).toLowerCase();
        val nomeWarp = args[args.length - 1].toLowerCase();

        if(nomeWarp.equals("list")) {
            LocRepository.withTipoLoc(PWARP)
                .list(player, nomePlayer, warps -> {
                    val warpsFormatados = warps.isEmpty() ? "Nenhum :(" : StringUtils.join(warps, ", ");

                    player.sendMessage(msg(USO_CORRETO_WARP, mapOf(
                        pair("player", nomePlayer),
                        pair("warps", warpsFormatados))));
                });
            return;
        }

        LocRepository.withTipoLoc(PWARP)
            .tp(player, nomePlayer, nomeWarp, ResultRunnable.create()
                .onSucesso(() -> player.sendMessage(msg(TELEPORTADO_COM_SUCESSO)))
                .onErro(() -> player.sendMessage(msg(WARP_NAO_ENCONTRADO))));
    }
}