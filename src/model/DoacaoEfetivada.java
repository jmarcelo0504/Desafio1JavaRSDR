package model;

import java.time.LocalDate;

public class DoacaoEfetivada {

    private int id;
    private ItemDoacao item;
    private Doador doador;
    private Beneficiario beneficiario;
    private LocalDate data;
    private String observacoes;

    public DoacaoEfetivada(int id, ItemDoacao item, Doador doador,
                           Beneficiario beneficiario, LocalDate data, String observacoes) {
        this.id = id;
        this.item = item;
        this.doador = doador;
        this.beneficiario = beneficiario;
        this.data = data;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public ItemDoacao getItem() {
        return item;
    }

    public Doador getDoador() {
        return doador;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public LocalDate getData() {
        return data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItem(ItemDoacao item) {
        this.item = item;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "Doação Nº " + id + ", Item: " + item.getNome() + ", Doador: " + doador.getNome() + ", Beneficiário: " + beneficiario.getNome() + ", Data: " + data + ", Observações: " + observacoes;
    }
}
