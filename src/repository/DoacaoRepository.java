package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Beneficiario;
import model.DoacaoEfetivada;
import model.Doador;
import model.ItemDoacao;
import util.DatabaseConnection;

public class DoacaoRepository {

    private final UsuarioRepository usuarioRepository;
    private final ItemRepository itemRepository;

    public DoacaoRepository(UsuarioRepository usuarioRepository, ItemRepository itemRepository) {
        this.usuarioRepository = usuarioRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * @param doacao a doação efetivada a ser persistida
     */
    public void registrar(DoacaoEfetivada doacao) {
        String sql = "INSERT INTO doacoes_efetivadas (item_id, doador_id, beneficiario_id, data, observacoes) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, doacao.getItem().getId());
            stmt.setInt(2, doacao.getDoador().getId());
            stmt.setInt(3, doacao.getBeneficiario().getId());
            stmt.setDate(4, Date.valueOf(doacao.getData()));
            stmt.setString(5, doacao.getObservacoes());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    doacao.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao registrar doação: " + e.getMessage());
        }
    }

    /**
     * @return lista de DoacoesEfetivadas
     */
    public List<DoacaoEfetivada> gerarRelatorio() {
        List<DoacaoEfetivada> doacoes = new ArrayList<>();
        String sql = "SELECT * FROM doacoes_efetivadas ORDER BY id";

        class DoacaoDados {
            int id;
            int itemId;
            int doadorId;
            int beneficiarioId;
            LocalDate data;
            String observacoes;

            DoacaoDados(int id, int itemId, int doadorId, int beneficiarioId, LocalDate data, String observacoes) {
                this.id = id;
                this.itemId = itemId;
                this.doadorId = doadorId;
                this.beneficiarioId = beneficiarioId;
                this.data = data;
                this.observacoes = observacoes;
            }
        }

        List<DoacaoDados> listaTemporaria = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Date sqlDate = rs.getDate("data");
                LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : LocalDate.now();

                listaTemporaria.add(new DoacaoDados(
                    rs.getInt("id"),
                    rs.getInt("item_id"),
                    rs.getInt("doador_id"),
                    rs.getInt("beneficiario_id"),
                    localDate,
                    rs.getString("observacoes")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório de doações: " + e.getMessage());
            return doacoes;
        }

        for (DoacaoDados dados : listaTemporaria) {
            ItemDoacao item = itemRepository.buscarPorId(dados.itemId);
            Doador doador = usuarioRepository.buscarDoadorPorId(dados.doadorId);
            Beneficiario beneficiario = usuarioRepository.buscarBeneficiarioPorId(dados.beneficiarioId);

            if (item != null && doador != null && beneficiario != null) {
                DoacaoEfetivada d = new DoacaoEfetivada(
                    dados.id,
                    item,
                    doador,
                    beneficiario,
                    dados.data,
                    dados.observacoes
                );
                doacoes.add(d);
            }
        }

        return doacoes;
    }
}