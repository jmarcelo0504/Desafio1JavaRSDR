import java.time.LocalDate;

public class ItemDoacao {
    private int id;
    private String nome;
    private String categoria;
    private String descricao;
    private int quantidade;
    private String estado;
    private LocalDate dataCadastro;
    private String status;

    public ItemDoacao(int id, String nome, String categoria, String descricao, int quantidade, String estado) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.estado = estado;
        this.dataCadastro = LocalDate.now();
        this.status = "Disponível";
    }


     public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }


    @Override
    public String toString() {
        return "ID: " + id + ", Nome do Item: " + nome + ", Categoria: " + categoria + "Descrição: " + descricao +", Quantidade: " + quantidade + ", Data de Cadastro: " + dataCadastro + ", Status: " + status;
    }
}