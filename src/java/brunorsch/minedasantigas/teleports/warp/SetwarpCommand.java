package brunorsch.minedasantigas.teleports.warp;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.Mensagem.SEM_PERMISSAO;
import static brunorsch.minedasantigas.locale.Mensagem.USO_CORRETO;
import static brunorsch.minedasantigas.locale.Mensagem.WARP_SETADO;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;

import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.teleports.WarpManager;

public class SetwarpCommand extends PlayerCommand {
    public SetwarpCommand() {
        super("setwarp", "Seta um novo warp");
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(!player.hasPermission("minedasantigas.setwarp")) {
            player.sendMessage(msg(SEM_PERMISSAO));
            return;
        }

        if(args.length != 1) {
            player.sendMessage(msg(USO_CORRETO, pair("comando", "/setwarp <Nome>")));
            return;
        }

        WarpManager.set(args[0], player.getLocation());
        player.sendMessage(msg(WARP_SETADO));
    }
}