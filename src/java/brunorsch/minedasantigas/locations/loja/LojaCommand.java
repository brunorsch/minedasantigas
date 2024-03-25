package brunorsch.minedasantigas.locations.loja;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.LocaleProvider.usoCorreto;
import static brunorsch.minedasantigas.locale.Mensagem.LOJA_BEM_VINDO;
import static brunorsch.minedasantigas.locale.Mensagem.LOJA_NAO_ENCONTRADA;
import static brunorsch.minedasantigas.locations.TipoLoc.LOJA;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;
import static org.bukkit.Sound.SUCCESSFUL_HIT;

import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.locations.LocRepository;
import brunorsch.minedasantigas.utils.ResultRunnable;
import lombok.val;

public class LojaCommand extends PlayerCommand {

    public LojaCommand() {
        super("loja", "Teleporta para uma loja");
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(args.length != 1) {
            player.sendMessage(usoCorreto("/loja <Nome da Loja>"));
            return;
        }

        val nomeLoja = args[0].toLowerCase();

        LocRepository.withTipoLoc(LOJA)
            .tp(player, nomeLoja, ResultRunnable.create()
                .onSucesso(() -> {
                    player.sendMessage(msg(LOJA_BEM_VINDO, pair("loja", nomeLoja)));
                    player.playSound(player.getLocation(), SUCCESSFUL_HIT, 1.0F, 1.0F);
                })
                .onErro(() -> player.sendMessage(msg(LOJA_NAO_ENCONTRADA))));
    }
}