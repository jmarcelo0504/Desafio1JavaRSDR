package model;

public class Beneficiario extends Usuario implements Comparable<Beneficiario> {

    private TipoBeneficiario tipo;
    private int prioridade;

    public Beneficiario(int id, String nome, String telefone, String email, String endereco, TipoBeneficiario tipo, int prioridade) {
        super(id, nome, telefone, email, endereco);
        this.tipo = tipo;
        this.prioridade = prioridade;
    }

    public TipoBeneficiario getTipo() {
        return tipo;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setTipo(TipoBeneficiario tipo) {
        this.tipo = tipo;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    @Override
    public int compareTo(Beneficiario outro) {
        return Integer.compare(outro.prioridade, this.prioridade);
    }

    @Override
    public String toString() {
        return "[BENEFICIÁRIO] " + super.toString() + ", Tipo: " + tipo + ", Prioridade: " + prioridade;
    }
}