package model;

import java.time.LocalDate;

public class ItemDoacao {

    private int id;
    private String nome;
    private String categoria;
    private String descricao;
    private int quantidade;
    private String estadoConservacao;
    private LocalDate dataCadastro;
    private StatusItem status;

    public ItemDoacao(int id, String nome, String categoria, String descricao, int quantidade, String estadoConservacao) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.estadoConservacao = estadoConservacao;
        this.dataCadastro = LocalDate.now();
        this.status = StatusItem.DISPONIVEL;
    }

    public ItemDoacao(int id, String nome, String categoria, String descricao, int quantidade, String estadoConservacao, LocalDate dataCadastro, StatusItem status) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.estadoConservacao = estadoConservacao;
        this.dataCadastro = dataCadastro;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getEstadoConservacao() {
        return estadoConservacao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public StatusItem getStatus() {
        return status;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setStatus(StatusItem status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Categoria: " + categoria + ", Descrição: " + descricao + ", Quantidade: " + quantidade + ", Estado: " + estadoConservacao + ", Data de Cadastro: " + dataCadastro + ", Status: " + status;
    }
}