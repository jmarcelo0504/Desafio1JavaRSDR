import model.*;
import java.util.ArrayList;

public class ServiceRSDR {

    private ArrayList<Doador> doadores = new ArrayList<>();
    private ArrayList<Beneficiario> beneficiarios = new ArrayList<>();
    private ArrayList<ItemDoacao> itens = new ArrayList<>();

    private int idDoador = 1;
    private int idBeneficiario = 1;
    private int idItem = 1;


    public void cadastrarDoador(String nome, String tel, String email, String end) {
        doadores.add(new Doador(idDoador++, nome, tel, email, end));
    }

    public void cadastrarBeneficiario(String nome, String tel, String email, String end, String tipo, int prioridade) {
        beneficiarios.add(new Beneficiario(idBeneficiario++, nome, tel, email, end, tipo, prioridade));
    }

    public void cadastrarItem(String nome, String cat, String desc, int qtd, String estado) {
        itens.add(new ItemDoacao(idItem++, nome, cat, desc, qtd, estado));
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
