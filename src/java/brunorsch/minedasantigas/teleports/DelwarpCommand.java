package brunorsch.minedasantigas.teleports;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.Mensagem.SEM_PERMISSAO;
import static brunorsch.minedasantigas.locale.Mensagem.USO_CORRETO;
import static brunorsch.minedasantigas.locale.Mensagem.WARP_DELETADO;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;

import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;

public class DelwarpCommand extends PlayerCommand {
    public DelwarpCommand() {
        super("delwarp", "Deleta um warp");
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(!player.hasPermission("minedasantigas.delwarp")) {
            player.sendMessage(msg(SEM_PERMISSAO));
            return;
        }

        if(args.length != 1) {
            player.sendMessage(msg(USO_CORRETO, pair("comando", "/delwarp <Nome>")));
            return;
        }

        WarpManager.delete(args[0]);
        player.sendMessage(msg(WARP_DELETADO));
    }
}