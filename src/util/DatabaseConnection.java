package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {

    private static String url = "jdbc:sqlite:rsdr_db.db";
    private static Connection conexao;

    static {
        Properties props = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                props.load(input);
                String dbUrl = props.getProperty("db.url");
                if (dbUrl != null && !dbUrl.trim().isEmpty()) {
                    url = dbUrl;
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível carregar db.properties, usando URL padrão: " + e.getMessage());
        }
    }

    private DatabaseConnection() {
        // Impede instanciação externa
    }

    /**
     * @return Connection ativa com o SQLite
     * @throws SQLException se houver falha ao conectar
     */
    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                conexao = DriverManager.getConnection(url);
                
                // Habilitar chaves estrangeiras no SQLite
                try (Statement stmt = conexao.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON;");
                }
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC do SQLite não encontrado. "
                        + "Verifique se a dependência está no pom.xml.", e);
            }
        }
        return conexao;
    }

    public static void fecharConexao() {
        if (conexao != null) {
            try {
                if (!conexao.isClosed()) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}