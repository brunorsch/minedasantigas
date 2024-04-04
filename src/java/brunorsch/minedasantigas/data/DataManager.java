package brunorsch.minedasantigas.data;

import static brunorsch.minedasantigas.DasAntigas.log;
import static java.sql.DriverManager.getConnection;
import static java.util.Objects.nonNull;
import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

import org.bukkit.Bukkit;

import brunorsch.minedasantigas.DasAntigas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

public class DataManager {
    private static File dbFile;

    @SneakyThrows
    public static void setup() {
        dbFile = criarArquivoDb();

        Class.forName("org.sqlite.JDBC");

        try(Connection connection = conn()) {} catch (SQLException e) {
            log().severe("Erro ao conectar no SQLite. Encerrando plugin");
            Bukkit.getPluginManager().disablePlugin(DasAntigas.inst());
            throw new RuntimeException(e);
        }

        Migrations.criarTabelaLocs();

        log().info("Banco de dados SQLite configurado com sucesso!");
    }

    private static Connection conn() throws SQLException {
        return getConnection("jdbc:sqlite:" + dbFile);
    }

    private static File criarArquivoDb() {
        File dbFile = new File(DasAntigas.inst().getDataFolder(), "sqlite.db");

        if(!dbFile.exists()) {
            try {
                val sucessoCriacao = dbFile.createNewFile();

                if(!sucessoCriacao) {
                    lancarErroCriacaoArquivo(null);
                }
            } catch (IOException e) {
                lancarErroCriacaoArquivo(e);
            }
        }

        return dbFile;
    }

    private static void lancarErroCriacaoArquivo(IOException ioException) {
        log().log(SEVERE, "Erro ao criar arquivo sqlite do plugin. Será necessário desativar.", ioException);
        throw new IllegalStateException();
    }

    public static QueryBuilder withSqlQuery(String query) {
        val queryBuilder = new QueryBuilder();
        queryBuilder.query = query;
        return queryBuilder;
    }

    @NoArgsConstructor
    public static class QueryBuilder {
        private String query;
        private SQLThrowableConsumer<PreparedStatement> bindLogic;
        private boolean finished;
        private Consumer<SQLException> onErrorLogic = (exception) -> {};

        public QueryBuilder bindParams(SQLThrowableConsumer<PreparedStatement> bindLogic) {
            this.bindLogic = bindLogic;
            return this;
        }

        public QueryBuilder onError(Consumer<SQLException> onErrorLogic) {
            this.onErrorLogic = onErrorLogic;
            return this;
        }

        public void thenQueryData(SQLThrowableConsumer<ResultSet> resultSetConsumer) {
            validateQueryFinished();

            finishQuery(EitherOperation.query(resultSetConsumer));
        }

        public void thenRunUpdate(Runnable afterUpdate) {
            validateQueryFinished();

            finishQuery(EitherOperation.update(afterUpdate));
        }

        public void thenRunUpdate() {
            thenRunUpdate(() -> {});
        }

        private void validateQueryFinished() {
            if(finished) {
                throw new IllegalStateException("A execução da query ja foi encerrada");
            }

            finished = true;
        }

        private void finishQuery(EitherOperation operation) {
            try(val conn = conn();
                val statement = conn.prepareStatement(query)) {
                if(nonNull(bindLogic)) bindLogic.accept(statement);


                if(operation.isQuery()) {
                    operation.getQueryLogic()
                        .accept(statement.executeQuery());
                }

                if(operation.isUpdate()) {
                    statement.executeUpdate();

                    operation.getUpdateLogic()
                        .run();
                }

            } catch (SQLException e) {
                log().warning("Erro ao executar query SQL: " + e);
                onErrorLogic.accept(e);
            }
        }

        @AllArgsConstructor
        @Getter
        private static class EitherOperation {
            private SQLThrowableConsumer<ResultSet> queryLogic;
            private Runnable updateLogic;

            public static EitherOperation query(SQLThrowableConsumer<ResultSet> query) {
                return new EitherOperation(query, null);
            }

            public static EitherOperation update(Runnable update) {
                return new EitherOperation(null, update);
            }

            public boolean isQuery() {
                return queryLogic != null;
            }

            public boolean isUpdate() {
                return updateLogic != null;
            }
        }

        public interface SQLThrowableConsumer<T> {
            void accept(T t) throws SQLException;
        }
    }

    public static void deepClose(ResultSet rs) throws SQLException {
        val st = rs.getStatement();
        val cnn = st.getConnection();
        rs.close();
        st.close();
        cnn.close();
    }
}