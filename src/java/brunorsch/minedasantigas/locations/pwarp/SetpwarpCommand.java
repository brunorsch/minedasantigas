package brunorsch.minedasantigas.locations.pwarp;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.LocaleProvider.usoCorreto;
import static brunorsch.minedasantigas.locale.Mensagem.WARP_SETADO;
import static brunorsch.minedasantigas.locations.TipoLoc.PWARP;

import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.locations.LocRepository;
import lombok.val;

public class SetpwarpCommand extends PlayerCommand {
    public SetpwarpCommand() {
        super("setpwarp", "Seta um warp de player");
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(args.length != 1) {
            player.sendMessage(usoCorreto("/setpwarp <Nome>"));
            return;
        }

        val nome = args[0].toLowerCase();

        LocRepository.withTipoLoc(PWARP)
            .save(player, nome, player.getLocation(), () -> player.sendMessage(msg(WARP_SETADO)));
    }
}