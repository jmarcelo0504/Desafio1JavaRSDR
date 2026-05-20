import model.*;
import java.util.ArrayList;

public class ServiceRSDR {

    private ArrayList<Doador> doadores = new ArrayList<>();
    private ArrayList<Beneficiario> beneficiarios = new ArrayList<>();
    private ArrayList<ItemDoacao> itens = new ArrayList<>();
    private ArrayList<Solicitacao> solicitacoes = new ArrayList<>();

    private int idDoador = 1;
    private int idBeneficiario = 1;
    private int idItem = 1;
    private int idSolicitacao = 1;


    public void cadastrarDoador(String nome, String telefone, String email, String endereco) {
        doadores.add(new Doador(idDoador++, nome, telefone, email, endereco));
    }

    public void cadastrarBeneficiario(String nome, String telefone, String email, String endereco, String tipo, int prioridade) {
        beneficiarios.add(new Beneficiario(idBeneficiario++, nome, telefone, email, endereco, tipo, prioridade));
    }

    public void cadastrarItem(String nome, String categoria, String descricao, int quantidade, String estado) {
        itens.add(new ItemDoacao(idItem++, nome, categoria, descricao, quantidade, estado));
    }

    public ArrayList<Doador> listarDoadores() {
        return doadores;
    }

    public ArrayList<Beneficiario> listarBeneficiarios() {
        return beneficiarios;
    }

    public ArrayList<ItemDoacao> listarItens() {
        return itens;
    }
}
