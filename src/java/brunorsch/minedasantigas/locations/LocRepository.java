package brunorsch.minedasantigas.locations;

import static brunorsch.minedasantigas.data.DataManager.deepClose;
import static brunorsch.minedasantigas.locale.LocaleProvider.msg;
import static brunorsch.minedasantigas.locale.Mensagem.ERRO_SQL;
import static java.util.Collections.emptySet;
import static lombok.AccessLevel.PROTECTED;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import brunorsch.minedasantigas.data.DataManager;
import brunorsch.minedasantigas.data.DataManager.QueryBuilder.SQLThrowableConsumer;
import brunorsch.minedasantigas.utils.ResultRunnable;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor(access = PROTECTED)
public class LocRepository {
    private final TipoLoc tipoLoc;

    public static LocRepository withTipoLoc(TipoLoc tipoLoc) {
        return new LocRepository(tipoLoc);
    }

    private void exists(Player player, String locOwner, String nome, SQLThrowableConsumer<ResultSet> onResult) {
        query("SELECT COUNT(1) FROM locs l WHERE l.nome = ? AND l.tipo = ?", player, locOwner, nome, onResult);
    }

    public void find(Player player, String locOwner, String nome, SQLThrowableConsumer<ResultSet> onResult) {
        query("SELECT * FROM locs l WHERE l.nome = ? AND l.tipo = ?", player, locOwner, nome, onResult);
    }

    private void query(String query, Player player, String locOwner, String nome, SQLThrowableConsumer<ResultSet> onResult) {
        val queryTratada = tipoLoc.tratarQuery(query);

        DataManager.withSqlQuery(queryTratada)
            .bindParams(ps -> {
                ps.setString(1, nome.toLowerCase());
                ps.setString(2, tipoLoc.name());

                if (tipoLoc.isOwnerBased()) {
                    ps.setString(3, locOwner.toLowerCase());
                }
            })
            .onError(ignored -> player.sendMessage(msg(ERRO_SQL)))
            .thenQueryData(onResult);
        }

    public void tp(Player player, String locOwner, String nome, ResultRunnable acao) {
        find(player, locOwner, nome, rs -> {
            if(rs.next()) {
                final String world = rs.getString("world");
                final double x = rs.getDouble("x");
                final double y = rs.getDouble("y");
                final double z = rs.getDouble("z");
                final float yaw = rs.getFloat("yaw");
                final float pitch = rs.getFloat("pitch");

                player.teleport(new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch));

                acao.getSucesso().run();
            } else {
                acao.getErro().run();
            }
        });
    }

    public void tp(Player player, String nome, ResultRunnable acao) {
        tp(player, player.getName(), nome, acao);
    }

    public void update(Player player, String locOwner, String nome, Location location, Runnable onResult) {
        DataManager.withSqlQuery(
                "UPDATE locs SET world=?, x=?, y=?, z=?, yaw=?, pitch=? WHERE player=? AND nome=? AND tipo=?"
            )
            .bindParams(ps -> {
                ps.setString(1, location.getWorld().getName());
                ps.setDouble(2, location.getX());
                ps.setDouble(3, location.getY());
                ps.setDouble(4, location.getZ());
                ps.setFloat(5, location.getYaw());
                ps.setDouble(6, location.getPitch());
                ps.setString(7, locOwner.toLowerCase());
                ps.setString(8, nome.toLowerCase());
                ps.setString(9, tipoLoc.name());
            })
            .onError(ignored -> player.sendMessage(msg(ERRO_SQL)))
            .thenRunUpdate(onResult);
    }

    public void insert(Player player, String locOwner, String nome, Location location, Runnable onResult) {
        DataManager.withSqlQuery(
                "INSERT INTO locs (player, nome, world, x, y, z, yaw, pitch, tipo) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
            .bindParams(ps -> {
                ps.setString(1, locOwner.toLowerCase());
                ps.setString(2, nome.toLowerCase());

                ps.setString(3, location.getWorld().getName());

                ps.setDouble(4, location.getX());
                ps.setDouble(5, location.getY());
                ps.setDouble(6, location.getZ());
                ps.setFloat(7, location.getYaw());
                ps.setDouble(8, location.getPitch());
                ps.setString(9, tipoLoc.name());
            })
            .onError(ignored -> player.sendMessage(msg(ERRO_SQL)))
            .thenRunUpdate(onResult);
    }

    public void save(Player player, String nome, Location location, Runnable onResult) {
        save(player, player.getName(), nome, location, onResult);
    }

    public void save(Player player, String locOwner, String nome, Location location, Runnable onResult) {
        exists(player, locOwner, nome, rs -> {
            rs.next();

            final int count = rs.getInt(1);

            deepClose(rs);

            if(count > 0) {
                update(player, locOwner, nome, location, onResult);
            } else insert(player, locOwner, nome, location, onResult);
        });
    }

    public void list(Player player, String locOwner, Consumer<Set<String>> onResult) {
        DataManager.withSqlQuery("SELECT l.nome FROM locs l WHERE l.player = ? AND l.tipo = ?")
            .bindParams(ps -> {
                ps.setString(1, locOwner.toLowerCase());
                ps.setString(2, tipoLoc.name());
            })
            .onError(ignored -> player.sendMessage(msg(ERRO_SQL)))
            .thenQueryData(rs -> {
                if(!rs.next()) {
                    onResult.accept(emptySet());
                }

                Set<String> locs = new HashSet<>();

                do {
                    locs.add(rs.getString(1));
                } while (rs.next());

                onResult.accept(locs);
            });
    }
}