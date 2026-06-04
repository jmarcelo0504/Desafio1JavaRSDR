package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Beneficiario;
import model.ItemDoacao;
import model.Solicitacao;
import model.StatusSolicitacao;
import util.DatabaseConnection;

public class SolicitacaoRepository {

    private final UsuarioRepository usuarioRepository;
    private final ItemRepository itemRepository;

    public SolicitacaoRepository(UsuarioRepository usuarioRepository, ItemRepository itemRepository) {
        this.usuarioRepository = usuarioRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * @param solicitacao a solicitação a ser persistida
     */
    public void salvar(Solicitacao solicitacao) {
        String sql = "INSERT INTO solicitacoes (beneficiario_id, item_id, quantidade_solicitada, "
                + "justificativa, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, solicitacao.getBeneficiario().getId());
            stmt.setInt(2, solicitacao.getItem().getId());
            stmt.setInt(3, solicitacao.getQuantidadeSolicitada());
            stmt.setString(4, solicitacao.getJustificativa());
            stmt.setString(5, solicitacao.getStatus().name());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                solicitacao.setId(rs.getInt(1));
            }

            System.out.println("Solicitação registrada com sucesso! ID: " + solicitacao.getId());

        } catch (SQLException e) {
            System.err.println("Erro ao salvar solicitação: " + e.getMessage());
        }
    }

    /**
     * @param id o ID da solicitação
     * @return a Solicitacao encontrada ou null
     */
    public Solicitacao buscarPorId(int id) {
        String sql = "SELECT * FROM solicitacoes WHERE id = ?";
        
        int beneficiarioId = 0;
        int itemId = 0;
        int quantidadeSolicitada = 0;
        String justificativa = null;
        String status = null;
        boolean encontrado = false;

        try (Connection conn = DatabaseConnection.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    beneficiarioId = rs.getInt("beneficiario_id");
                    itemId = rs.getInt("item_id");
                    quantidadeSolicitada = rs.getInt("quantidade_solicitada");
                    justificativa = rs.getString("justificativa");
                    status = rs.getString("status");
                    encontrado = true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar solicitação: " + e.getMessage());
        }

        if (!encontrado) {
            return null;
        }

        Beneficiario beneficiario = usuarioRepository.buscarBeneficiarioPorId(beneficiarioId);
        ItemDoacao item = itemRepository.buscarPorId(itemId);

        if (beneficiario == null || item == null) {
            return null;
        }

        return new Solicitacao(
                id,
                beneficiario,
                item,
                quantidadeSolicitada,
                justificativa,
                StatusSolicitacao.valueOf(status)
        );
    }

    /**
     * @return lista de Solicitações
     */
    public List<Solicitacao> listarTodas() {
        List<Solicitacao> solicitacoes = new ArrayList<>();
        String sql = "SELECT * FROM solicitacoes ORDER BY id";

        class SolicitacaoDados {
            int id;
            int beneficiarioId;
            int itemId;
            int quantidadeSolicitada;
            String justificativa;
            String status;

            SolicitacaoDados(int id, int beneficiarioId, int itemId, int quantidadeSolicitada, String justificativa, String status) {
                this.id = id;
                this.beneficiarioId = beneficiarioId;
                this.itemId = itemId;
                this.quantidadeSolicitada = quantidadeSolicitada;
                this.justificativa = justificativa;
                this.status = status;
            }
        }

        List<SolicitacaoDados> listaTemporaria = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaTemporaria.add(new SolicitacaoDados(
                    rs.getInt("id"),
                    rs.getInt("beneficiario_id"),
                    rs.getInt("item_id"),
                    rs.getInt("quantidade_solicitada"),
                    rs.getString("justificativa"),
                    rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar solicitações do banco: " + e.getMessage());
            return solicitacoes;
        }

        for (SolicitacaoDados dados : listaTemporaria) {
            Beneficiario beneficiario = usuarioRepository.buscarBeneficiarioPorId(dados.beneficiarioId);
            ItemDoacao item = itemRepository.buscarPorId(dados.itemId);

            if (beneficiario != null && item != null) {
                Solicitacao s = new Solicitacao(
                        dados.id,
                        beneficiario,
                        item,
                        dados.quantidadeSolicitada,
                        dados.justificativa,
                        StatusSolicitacao.valueOf(dados.status)
                );
                solicitacoes.add(s);
            }
        }

        return solicitacoes;
    }

    /**
     *
     * @param id o ID da solicitação
     * @param novoStatus o novo status
     */
    public void atualizarStatus(int id, StatusSolicitacao novoStatus) {
        String sql = "UPDATE solicitacoes SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus.name());
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status da solicitação: " + e.getMessage());
        }
    }
}