package service;

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

    public void solicitarItem(int indiceBeneficiario, int indiceItem, int quantidade, String justificativa) {

        if (indiceBeneficiario < 0 || indiceBeneficiario >= beneficiarios.size()) {

            System.out.println("Beneficiário inválido.");
            return;
        }

        if (indiceItem < 0 || indiceItem >= itens.size()) {

            System.out.println("Item inválido.");
            return;
        }

        if (quantidade <= 0) {
            System.out.println("Quantidade inválida.");
            return;
        }

        Beneficiario beneficiario = beneficiarios.get(indiceBeneficiario);

        ItemDoacao item = itens.get(indiceItem);

        if (item.getStatus().equalsIgnoreCase("Entregue")) {
            System.out.println("Item indisponível.");
            return;
        }

        if (quantidade > item.getQuantidade()) {
            System.out.println("Quantidade indisponível.");
            return;
        }

        item.setQuantidade(item.getQuantidade() - quantidade);

        if (item.getQuantidade() == 0) {
            item.setStatus("Entregue");
        } else {
            item.setStatus("Reservado");
        }

        Solicitacao solicitacao = new Solicitacao( idSolicitacao++, beneficiario, item, quantidade, justificativa, "Aprovada");
        solicitacoes.add(solicitacao);
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

    public ArrayList<Solicitacao> listarSolicitacoes() {
        return solicitacoes;
    }
}
