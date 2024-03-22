package brunorsch.minedasantigas.teleports.spawn;

import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.Mensagem.SPAWN_NAO_SETADO;
import static brunorsch.minedasantigas.locale.Mensagem.TELEPORTADO_COM_SUCESSO;
import static brunorsch.minedasantigas.utils.OptionalUtils.ifPresentOrElse;

import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;
import brunorsch.minedasantigas.teleports.ConfigLocationHelper;

public class SpawnCommand extends PlayerCommand {

    public SpawnCommand() {
        super("spawn", "Teleporta para o spawn");
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        ifPresentOrElse(ConfigLocationHelper.get("Spawn"),
            spawnLocation -> {
                player.teleport(spawnLocation);
                player.sendMessage(msg(TELEPORTADO_COM_SUCESSO));
            },
            () -> player.sendMessage(msg(SPAWN_NAO_SETADO)));
    }
}