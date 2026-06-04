package model;

public class Solicitacao {

    private int id;
    private Beneficiario beneficiario;
    private ItemDoacao item;
    private int quantidadeSolicitada;
    private String justificativa;
    private StatusSolicitacao status;

    public Solicitacao(int id, Beneficiario beneficiario, ItemDoacao item, int quantidadeSolicitada, String justificativa, StatusSolicitacao status) {
        this.id = id;
        this.beneficiario = beneficiario;
        this.item = item;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.justificativa = justificativa;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public ItemDoacao getItem() {
        return item;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public void setItem(ItemDoacao item) {
        this.item = item;
    }

    public void setQuantidadeSolicitada(int quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Solicitação Nº " + id + ", Beneficiário: " + beneficiario.getNome() + ", Item: " + item.getNome() + ", Quantidade: " + quantidadeSolicitada + ", Justificativa: " + justificativa + ", Status: " + status;
    }
}