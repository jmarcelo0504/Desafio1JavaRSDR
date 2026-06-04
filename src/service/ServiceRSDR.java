package service;

import model.*;
import repository.*;

import java.time.LocalDate;
import java.util.List;

public class ServiceRSDR {

    private final UsuarioRepository usuarioRepository;
    private final ItemRepository itemRepository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final DoacaoRepository doacaoRepository;

    public ServiceRSDR(UsuarioRepository usuarioRepository, ItemRepository itemRepository,
                       SolicitacaoRepository solicitacaoRepository, DoacaoRepository doacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.itemRepository = itemRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.doacaoRepository = doacaoRepository;
    }

    public void cadastrarDoador(String nome, String telefone, String email, String endereco) {
        Doador doador = new Doador(0, nome, telefone, email, endereco);
        usuarioRepository.salvar(doador);
    }

    public void cadastrarBeneficiario(String nome, String telefone, String email,
                                       String endereco, TipoBeneficiario tipo, int prioridade) {
        Beneficiario beneficiario = new Beneficiario(0, nome, telefone, email, endereco, tipo, prioridade);
        usuarioRepository.salvar(beneficiario);
    }

    public void cadastrarItem(String nome, String categoria, String descricao,
                              int quantidade, String estadoConservacao) {
        ItemDoacao item = new ItemDoacao(0, nome, categoria, descricao, quantidade, estadoConservacao);
        itemRepository.salvar(item);
    }

    /**
     * @param idBeneficiario ID do beneficiário solicitante
     * @param idItem         ID do item solicitado
     * @param quantidade     quantidade desejada
     * @param justificativa  justificativa da solicitação
     */
    public void solicitarItem(int idBeneficiario, int idItem, int quantidade, String justificativa) {
        Beneficiario beneficiario = usuarioRepository.buscarBeneficiarioPorId(idBeneficiario);
        if (beneficiario == null) {
            System.out.println("Beneficiário com ID " + idBeneficiario + " não encontrado.");
            return;
        }

        ItemDoacao item = itemRepository.buscarPorId(idItem);
        if (item == null) {
            System.out.println("Item com ID " + idItem + " não encontrado.");
            return;
        }

        if (item.getStatus() != StatusItem.DISPONIVEL) {
            System.out.println("Item indisponível. Status atual: " + item.getStatus());
            return;
        }

        if (quantidade <= 0) {
            System.out.println("Quantidade inválida. Deve ser maior que zero.");
            return;
        }

        if (quantidade > item.getQuantidade()) {
            System.out.println("Quantidade solicitada (" + quantidade
                    + ") excede o estoque disponível (" + item.getQuantidade() + ").");
            return;
        }

        Solicitacao solicitacao = new Solicitacao(
                0, beneficiario, item, quantidade, justificativa, StatusSolicitacao.PENDENTE
        );
        solicitacaoRepository.salvar(solicitacao);
    }

    // ==================== APROVAÇÃO ====================

    /**
     * @param idSolicitacao ID da solicitação a ser aprovada
     */
    public void aprovarSolicitacao(int idSolicitacao) {
        Solicitacao solicitacao = solicitacaoRepository.buscarPorId(idSolicitacao);
        if (solicitacao == null) {
            System.out.println("Solicitação com ID " + idSolicitacao + " não encontrada.");
            return;
        }

        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE) {
            System.out.println("Solicitação não está pendente. Status atual: " + solicitacao.getStatus());
            return;
        }

        ItemDoacao item = solicitacao.getItem();

        if (item.getStatus() != StatusItem.DISPONIVEL) {
            System.out.println("Item indisponível para aprovação. Status atual: " + item.getStatus());
            return;
        }

        if (solicitacao.getQuantidadeSolicitada() > item.getQuantidade()) {
            System.out.println("Estoque insuficiente. Solicitado: " + solicitacao.getQuantidadeSolicitada()
                    + ", Disponível: " + item.getQuantidade());
            return;
        }

        int novaQuantidade = item.getQuantidade() - solicitacao.getQuantidadeSolicitada();
        StatusItem novoStatus = (novaQuantidade == 0) ? StatusItem.RESERVADO : StatusItem.DISPONIVEL;
        itemRepository.atualizarEstoque(item.getId(), novaQuantidade, novoStatus);

        solicitacaoRepository.atualizarStatus(idSolicitacao, StatusSolicitacao.APROVADA);

        System.out.println("Solicitação Nº " + idSolicitacao + " aprovada com sucesso!");
        if (novaQuantidade == 0) {
            System.out.println("Estoque do item \"" + item.getNome() + "\" totalmente reservado para entrega.");
        } else {
            System.out.println("Estoque restante do item \"" + item.getNome() + "\": " + novaQuantidade);
        }
    }

    /**
     * @param idSolicitacao ID da solicitação a ser rejeitada
     */
    public void rejeitarSolicitacao(int idSolicitacao) {
        Solicitacao solicitacao = solicitacaoRepository.buscarPorId(idSolicitacao);
        if (solicitacao == null) {
            System.out.println("Solicitação com ID " + idSolicitacao + " não encontrada.");
            return;
        }

        if (solicitacao.getStatus() == StatusSolicitacao.REJEITADA) {
            System.out.println("A solicitação já está rejeitada.");
            return;
        }

        ItemDoacao item = solicitacao.getItem();

        if (solicitacao.getStatus() == StatusSolicitacao.APROVADA) {
            int novaQuantidade = item.getQuantidade() + solicitacao.getQuantidadeSolicitada();
            itemRepository.atualizarEstoque(item.getId(), novaQuantidade, StatusItem.DISPONIVEL);
            System.out.println("Estoque devolvido para o item \"" + item.getNome() + "\". Nova quantidade: " + novaQuantidade);
        }

        solicitacaoRepository.atualizarStatus(idSolicitacao, StatusSolicitacao.REJEITADA);
        System.out.println("Solicitação Nº " + idSolicitacao + " rejeitada com sucesso.");
    }

    /**
     * @param idSolicitacao ID da solicitação aprovada
     * @param idDoador      ID do doador responsável pela entrega
     * @param observacoes   observações sobre a entrega
     */
    public void concluirEntrega(int idSolicitacao, int idDoador, String observacoes) {
        Solicitacao solicitacao = solicitacaoRepository.buscarPorId(idSolicitacao);
        if (solicitacao == null) {
            System.out.println("Solicitação com ID " + idSolicitacao + " não encontrada.");
            return;
        }

        if (solicitacao.getStatus() != StatusSolicitacao.APROVADA) {
            System.out.println("A solicitação não está aprovada. Status atual: " + solicitacao.getStatus());
            return;
        }

        Doador doador = usuarioRepository.buscarDoadorPorId(idDoador);
        if (doador == null) {
            System.out.println("Doador com ID " + idDoador + " não encontrado.");
            return;
        }

        DoacaoEfetivada doacao = new DoacaoEfetivada(
                0,
                solicitacao.getItem(),
                doador,
                solicitacao.getBeneficiario(),
                LocalDate.now(),
                observacoes
        );

        doacaoRepository.registrar(doacao);

        ItemDoacao itemAtualizado = itemRepository.buscarPorId(solicitacao.getItem().getId());
        if (itemAtualizado.getQuantidade() == 0) {
            itemRepository.atualizarEstoque(itemAtualizado.getId(), 0, StatusItem.ENTREGUE);
            System.out.println("O item \"" + itemAtualizado.getNome() + "\" foi totalmente entregue.");
        }

        System.out.println("Entrega concluída e doação efetivada com sucesso!");
    }

    public List<Doador> listarDoadores() {
        return usuarioRepository.listarDoadores();
    }

    public List<Beneficiario> listarBeneficiarios() {
        return usuarioRepository.listarBeneficiarios();
    }

    public List<ItemDoacao> listarItens() {
        return itemRepository.listarTodos();
    }

    public List<Solicitacao> listarSolicitacoes() {
        return solicitacaoRepository.listarTodas();
    }

    public List<DoacaoEfetivada> gerarRelatorio() {
        return doacaoRepository.gerarRelatorio();
    }

    public List<ItemDoacao> listarItensPorCategoria(String categoria) {
        return itemRepository.buscarPorCategoria(categoria);
    }

    public List<ItemDoacao> listarItensPorStatus(StatusItem status) {
        return itemRepository.buscarPorStatus(status);
    }
}
