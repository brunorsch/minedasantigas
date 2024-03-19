package brunorsch.minedasantigas.fly;

import static java.util.Collections.singletonList;

import org.bukkit.entity.Player;

import brunorsch.minedasantigas.command.PlayerCommand;

public class FlyCommand extends PlayerCommand {
    public FlyCommand() {
        super("fly", "Ativa o modo fly", singletonList("f"));
    }

    @Override
    public void onCommand(final Player player, final String[] args) {
        boolean newFly = !player.getAllowFlight();

        player.setAllowFlight(newFly);
        player.sendMessage(newFly ? "§aFly ativado!" : "§cFly desativado!");
    }
}