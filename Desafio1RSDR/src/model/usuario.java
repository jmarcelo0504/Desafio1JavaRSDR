public class usuario {
    protected int id;
    protected String nome;
    protected String telefone;
    protected String email;
    protected String endereco;

    public Usuario(int id, String nome, String telefone, String email, String endereco) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Telefone: " + telefone;
    }
}
