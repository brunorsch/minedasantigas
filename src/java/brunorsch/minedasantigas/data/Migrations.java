package brunorsch.minedasantigas.data;

public class Migrations {
    public static void criarTabelaLocs() {
        DataManager.withSqlQuery(
                "CREATE TABLE IF NOT EXISTS locs ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "player TEXT,\n"
                    + "nome TEXT,\n"
                    + "world TEXT,\n"
                    + "x REAL,\n"
                    + "y REAL,\n"
                    + "z REAL,\n"
                    + "yaw REAL,\n"
                    + "pitch REAL,\n"
                    + "tipo TEXT CHECK(tipo IN ('HOME', 'LOJA', 'PWARP')),\n"
                    + "CONSTRAINT loc_uk UNIQUE (nome, player, tipo) \n"
                    + ");")
            .thenRunUpdate();
    }
}