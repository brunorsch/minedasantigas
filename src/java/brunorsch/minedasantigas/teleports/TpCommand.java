package brunorsch.minedasantigas.teleports;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.LocaleProvider.usoCorreto;
import static brunorsch.minedasantigas.locale.Mensagem.MUNDO_NAO_ENCONTRADO;
import static brunorsch.minedasantigas.locale.Mensagem.PLAYER_NAO_ENCONTRADO;
import static brunorsch.minedasantigas.locale.Mensagem.TELEPORTADO_COM_SUCESSO;
import static brunorsch.minedasantigas.locale.Mensagem.XYZ_DEVE_SER_NUMERO;
import static java.util.Collections.singletonList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;
import lombok.val;

public class TpCommand extends PlayerCommand {
    public TpCommand() {
        super("tp", "Teleporta para uma posição ou um jogador", singletonList("tppos"));
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        if(args.length == 0 || args.length > 4) {
            player.sendMessage(usoCorreto("/tp <Jogador> - Ir até outro jogador"));
            player.sendMessage(usoCorreto("/tp <@/Jogador> <Outro jogador> - Puxar outro jogador. Com o @ puxa para sí."));
            player.sendMessage(usoCorreto("/tp [Jogador] <X> <Y> <Z>"));
            player.sendMessage(usoCorreto("/tp [Mundo] <X> <Y> <Z>"));
            return;
        }

        if(args.length == 1) {
            tpToAlvo(player, player, Bukkit.getPlayer(args[0]));
            return;
        }

        if(args.length == 2) {
            Player alvo = args[0].equals("@") ? player : Bukkit.getPlayer(args[0]);
            Player teleportando = Bukkit.getPlayer(args[1]);

            tpToAlvo(player,  alvo, teleportando);
            return;
        }

        String x, y, z;

        if(args.length == 3) {
            x = args[0];
            y = args[1];
            z = args[2];
            tpToLoc(player, player.getWorld(), new CoordsValidator(x, y, z));
            return;
        }

        val playerAlvo = (player.getName().equalsIgnoreCase(args[0])) ? player : Bukkit.getPlayer(args[0]);
        val mundo = (playerAlvo == null) ? Bukkit.getWorld(args[0]) : player.getWorld();

        x = args[1];
        y = args[2];
        z = args[3];

        val coords = new CoordsValidator(x, y, z);

        if(playerAlvo != null) {
            tpPlayerAlvoToLoc(player, playerAlvo, mundo, coords);
        } else {
            tpToLoc(player, mundo, coords);
        }
    }

    private void tpToAlvo(Player playerSolicitante, Player playerTeleportando, Player playerAlvo) {
        if(playerAlvo == null || playerTeleportando == null) {
            playerSolicitante.sendMessage(msg(PLAYER_NAO_ENCONTRADO));
            return;
        }

        playerTeleportando.teleport(playerAlvo);
        playerSolicitante.sendMessage(msg(TELEPORTADO_COM_SUCESSO));
    }

    private void tpToLoc(Player player, World mundo, CoordsValidator coords) {
        tpPlayerAlvoToLoc(player, player, mundo, coords);
    }

    private void tpPlayerAlvoToLoc(Player player, Player playerAlvo, World mundo, CoordsValidator coords) {
        if(mundo == null) {
            player.sendMessage(msg(MUNDO_NAO_ENCONTRADO));
            return;
        }

        if(!coords.isValid()) {
            player.sendMessage(msg(XYZ_DEVE_SER_NUMERO));
        }

        playerAlvo.teleport(new Location(mundo, coords.getX(), coords.getY(), coords.getZ()));
        player.sendMessage(msg(TELEPORTADO_COM_SUCESSO));
    }
}