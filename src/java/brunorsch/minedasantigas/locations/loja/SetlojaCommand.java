package brunorsch.minedasantigas.locations.loja;

import static brunorsch.minedasantigas.data.DataManager.deepClose;
import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.LocaleProvider.usoCorreto;
import static brunorsch.minedasantigas.locale.Mensagem.LOJA_CRIADA;
import static brunorsch.minedasantigas.locations.TipoLoc.LOJA;

import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.locale.Mensagem;
import brunorsch.minedasantigas.locations.LocRepository;
import lombok.val;

public class SetlojaCommand extends PlayerCommand {

    public SetlojaCommand() {
        super("setloja", "Seta uma nova loja no local atual");
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(args.length != 1) {
            player.sendMessage(usoCorreto("/setloja <Nome da loja>"));
            return;
        }

        val nomeLoja = args[0].toLowerCase();

        val repo = LocRepository.withTipoLoc(LOJA);

        repo.find(player, player.getName(), nomeLoja, rs -> {
            if(rs.next() && !(rs.getString("player").equalsIgnoreCase(player.getName()))) {
                player.sendMessage(msg(Mensagem.LOJA_JA_EXISTE));
                return;
            }

            deepClose(rs);

            repo.save(player, nomeLoja, player.getLocation(), () -> player.sendMessage(msg(LOJA_CRIADA)));
        });
    }
}