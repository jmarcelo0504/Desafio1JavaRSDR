package repository;

import model.Beneficiario;
import model.Doador;
import model.TipoBeneficiario;
import model.Usuario;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    /**
     * @param usuario o objeto a ser persistido
     */
    public void salvar(Usuario usuario) {
        if (usuario instanceof Doador) {
            salvarDoador((Doador) usuario);
        } else if (usuario instanceof Beneficiario) {
            salvarBeneficiario((Beneficiario) usuario);
        }
    }

    private void salvarDoador(Doador doador) {
        String sql = "INSERT INTO doadores (nome, telefone, email, endereco) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, doador.getNome());
            stmt.setString(2, doador.getTelefone());
            stmt.setString(3, doador.getEmail());
            stmt.setString(4, doador.getEndereco());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                doador.setId(rs.getInt(1));
            }

            System.out.println("Doador cadastrado com sucesso! ID: " + doador.getId());

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar doador: " + e.getMessage());
        }
    }

    private void salvarBeneficiario(Beneficiario beneficiario) {
        String sql = "INSERT INTO beneficiarios (nome, telefone, email, endereco, tipo, prioridade) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, beneficiario.getNome());
            stmt.setString(2, beneficiario.getTelefone());
            stmt.setString(3, beneficiario.getEmail());
            stmt.setString(4, beneficiario.getEndereco());
            stmt.setString(5, beneficiario.getTipo().name());
            stmt.setInt(6, beneficiario.getPrioridade());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                beneficiario.setId(rs.getInt(1));
            }

            System.out.println("Beneficiário cadastrado com sucesso! ID: " + beneficiario.getId());

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar beneficiário: " + e.getMessage());
        }
    }

    /**
     * @param id o ID do doador
     * @return o Doador encontrado ou null se não existir
     */
    public Doador buscarDoadorPorId(int id) {
        String sql = "SELECT * FROM doadores WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Doador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("endereco")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar doador: " + e.getMessage());
        }
        return null;
    }

    /**
     * @param id o ID do beneficiário
     * @return o Beneficiario encontrado ou null se não existir
     */
    public Beneficiario buscarBeneficiarioPorId(int id) {
        String sql = "SELECT * FROM beneficiarios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Beneficiario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("endereco"),
                        TipoBeneficiario.valueOf(rs.getString("tipo")),
                        rs.getInt("prioridade")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar beneficiário: " + e.getMessage());
        }
        return null;
    }

    /**
     * @param id o ID do usuário
     * @return o Usuario encontrado ou null
     */
    public Usuario buscarPorId(int id) {
        Doador doador = buscarDoadorPorId(id);
        if (doador != null) {
            return doador;
        }
        return buscarBeneficiarioPorId(id);
    }

    /**
     * @return lista de Doadores
     */
    public List<Doador> listarDoadores() {
        List<Doador> doadores = new ArrayList<>();
        String sql = "SELECT * FROM doadores ORDER BY id";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                doadores.add(new Doador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("endereco")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar doadores: " + e.getMessage());
        }
        return doadores;
    }

    /**
     * @return lista de Beneficiarios
     */
    public List<Beneficiario> listarBeneficiarios() {
        List<Beneficiario> beneficiarios = new ArrayList<>();
        String sql = "SELECT * FROM beneficiarios ORDER BY id";

        try (Connection conn = DatabaseConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                beneficiarios.add(new Beneficiario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("endereco"),
                        TipoBeneficiario.valueOf(rs.getString("tipo")),
                        rs.getInt("prioridade")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar beneficiários: " + e.getMessage());
        }
        return beneficiarios;
    }

    /**
     * @return lista de Usuarios
     */
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(listarDoadores());
        usuarios.addAll(listarBeneficiarios());
        return usuarios;
    }
}
