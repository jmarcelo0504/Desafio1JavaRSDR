package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void inicializar() {
        String criarTabelaDoadores = """
                CREATE TABLE IF NOT EXISTS doadores (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome VARCHAR(150) NOT NULL,
                    telefone VARCHAR(20),
                    email VARCHAR(150),
                    endereco VARCHAR(255)
                );
                """;

        String criarTabelaBeneficiarios = """
                CREATE TABLE IF NOT EXISTS beneficiarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome VARCHAR(150) NOT NULL,
                    telefone VARCHAR(20),
                    email VARCHAR(150),
                    endereco VARCHAR(255),
                    tipo VARCHAR(20) NOT NULL,
                    prioridade INT NOT NULL
                );
                """;

        String criarTabelaItens = """
                CREATE TABLE IF NOT EXISTS itens_doacao (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome VARCHAR(150) NOT NULL,
                    categoria VARCHAR(100),
                    descricao TEXT,
                    quantidade INT NOT NULL,
                    estado_conservacao VARCHAR(100),
                    data_cadastro DATE NOT NULL,
                    status VARCHAR(20) NOT NULL
                );
                """;

        String criarTabelaSolicitacoes = """
                CREATE TABLE IF NOT EXISTS solicitacoes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    beneficiario_id INT NOT NULL REFERENCES beneficiarios(id),
                    item_id INT NOT NULL REFERENCES itens_doacao(id),
                    quantidade_solicitada INT NOT NULL,
                    justificativa TEXT,
                    status VARCHAR(20) NOT NULL
                );
                """;

        String criarTabelaDoacoes = """
                CREATE TABLE IF NOT EXISTS doacoes_efetivadas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    item_id INT NOT NULL REFERENCES itens_doacao(id),
                    doador_id INT NOT NULL REFERENCES doadores(id),
                    beneficiario_id INT NOT NULL REFERENCES beneficiarios(id),
                    data DATE NOT NULL,
                    observacoes TEXT
                );
                """;

        try {
            Connection conexao = DatabaseConnection.getConexao();
            Statement stmt = conexao.createStatement();

            stmt.execute(criarTabelaDoadores);
            stmt.execute(criarTabelaBeneficiarios);
            stmt.execute(criarTabelaItens);
            stmt.execute(criarTabelaSolicitacoes);
            stmt.execute(criarTabelaDoacoes);

            stmt.close();
            System.out.println("Tabelas verificadas/criadas com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
