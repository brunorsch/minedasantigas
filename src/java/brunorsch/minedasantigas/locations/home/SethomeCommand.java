package brunorsch.minedasantigas.locations.home;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.LocaleProvider.usoCorreto;
import static brunorsch.minedasantigas.locale.Mensagem.HOME_SETADA;
import static brunorsch.minedasantigas.locations.TipoLoc.HOME;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;

import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.locations.LocRepository;
import lombok.val;

public class SethomeCommand extends PlayerCommand {
    public SethomeCommand() {
        super("sethome", "Seta uma nova home no local atual");
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(args.length != 1) {
            player.sendMessage(usoCorreto("/sethome <Nome da home>"));
            player.sendMessage("§e[§6§l!§r§e] Dica: §a/sethome home§e define a home padrão, que tu é teleportado "
                + "ao usar o §d/h");
            return;
        }

        val nomeCasa = args[0];

        LocRepository.withTipoLoc(HOME)
            .save(player, nomeCasa, player.getLocation(), () -> {
            player.sendMessage(msg(HOME_SETADA, pair("home", nomeCasa)));
        });
    }
}