package brunorsch.minedasantigas.locations.home;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.Mensagem.HOME_NAO_ENCONTRADA;
import static brunorsch.minedasantigas.locale.Mensagem.TELEPORTADO_COM_SUCESSO;
import static brunorsch.minedasantigas.locale.Mensagem.TELEPORTADO_PARA_CAMA;
import static brunorsch.minedasantigas.locations.TipoLoc.HOME;
import static java.util.Collections.singletonList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.locations.LocRepository;
import brunorsch.minedasantigas.utils.ResultRunnable;
import lombok.val;

public class HomeCommand extends PlayerCommand {
    private static final String HOME_PADRAO = "home";
    public HomeCommand() {
        super("home", "Teleporte para sua casa", singletonList("h"));
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        val toHomePadrao = args.length == 0;
        val nomeHome = toHomePadrao ? HOME_PADRAO : args[0].toLowerCase();

        LocRepository.withTipoLoc(HOME)
            .tp(player, nomeHome, ResultRunnable.create()
                .onSucesso(() -> player.sendMessage(msg(TELEPORTADO_COM_SUCESSO)))
                .onErro(() -> {
                    if(!toHomePadrao || !fallbackToBed(player)) {
                        player.sendMessage(msg(HOME_NAO_ENCONTRADA));
                    }
                }));
    }

    private boolean fallbackToBed(Player player) {
        if(player.getBedSpawnLocation() == null) return false;

        player.teleport(player.getBedSpawnLocation(), TeleportCause.COMMAND);
        player.sendMessage(msg(TELEPORTADO_PARA_CAMA));

        return true;
    }
}