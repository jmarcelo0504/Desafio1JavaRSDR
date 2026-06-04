package repository;

import model.ItemDoacao;
import model.StatusItem;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository {

    /**
     * @param item o item a ser persistido
     */
    public void salvar(ItemDoacao item) {
        String sql = "INSERT INTO itens_doacao (nome, categoria, descricao, quantidade, "
                + "estado_conservacao, data_cadastro, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getCategoria());
            stmt.setString(3, item.getDescricao());
            stmt.setInt(4, item.getQuantidade());
            stmt.setString(5, item.getEstadoConservacao());
            stmt.setDate(6, Date.valueOf(item.getDataCadastro()));
            
            stmt.setString(7, item.getStatus() != null ? item.getStatus().name() : StatusItem.DISPONIVEL.name());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));
            }

            System.out.println("Item cadastrado com sucesso! ID: " + item.getId());

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar item: " + e.getMessage());
        }
    }

    /**
     * @param id o ID do item
     * @return o ItemDoacao encontrado ou null
     */
    public ItemDoacao buscarPorId(int id) {
        String sql = "SELECT * FROM itens_doacao WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return construirItem(rs);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar item: " + e.getMessage());
        }
        return null;
    }

    /**
     * @return lista de ItemDoacao
     */
    public List<ItemDoacao> listarTodos() {
        List<ItemDoacao> itens = new ArrayList<>();
        String sql = "SELECT * FROM itens_doacao ORDER BY id";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                itens.add(construirItem(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar itens: " + e.getMessage());
        }
        return itens;
    }

    /**
     * @param id             o ID do item
     * @param novaQuantidade a nova quantidade em estoque
     * @param status         o novo status do item
     */
    public void atualizarEstoque(int id, int novaQuantidade, StatusItem status) {
        String sql = "UPDATE itens_doacao SET quantidade = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Math.max(0, novaQuantidade));
            stmt.setString(2, status.name());
            stmt.setInt(3, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar estoque: " + e.getMessage());
        }
    }

    public List<ItemDoacao> buscarPorCategoria(String categoria) {
        List<ItemDoacao> itens = new ArrayList<>();
        String sql = "SELECT * FROM itens_doacao WHERE LOWER(categoria) = LOWER(?) ORDER BY id";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    itens.add(construirItem(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar itens por categoria: " + e.getMessage());
        }
        return itens;
    }

    public List<ItemDoacao> buscarPorStatus(StatusItem status) {
        List<ItemDoacao> itens = new ArrayList<>();
        String sql = "SELECT * FROM itens_doacao WHERE status = ? ORDER BY id";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    itens.add(construirItem(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar itens por status: " + e.getMessage());
        }
        return itens;
    }

    private ItemDoacao construirItem(ResultSet rs) throws SQLException {
        return new ItemDoacao(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("categoria"),
                rs.getString("descricao"),
                rs.getInt("quantidade"),
                rs.getString("estado_conservacao"),
                rs.getDate("data_cadastro").toLocalDate(),
                StatusItem.valueOf(rs.getString("status"))
        );
    }
}