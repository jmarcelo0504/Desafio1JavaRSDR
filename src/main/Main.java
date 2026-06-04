package main;

import model.TipoBeneficiario;
import model.StatusItem;
import repository.*;
import service.ServiceRSDR;
import util.*;


public class Main {

    public static void main(String[] args) {

        DatabaseInitializer.inicializar();

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ItemRepository itemRepository = new ItemRepository();
        SolicitacaoRepository solicitacaoRepository = new SolicitacaoRepository(usuarioRepository, itemRepository);
        DoacaoRepository doacaoRepository = new DoacaoRepository(usuarioRepository, itemRepository);

        ServiceRSDR sistema = new ServiceRSDR(usuarioRepository, itemRepository, solicitacaoRepository, doacaoRepository);

        int opcao;

        do {
            Menu.exibirMenu();
            opcao = Entrada.lerInt("Escolha uma opção:");

            switch (opcao) {

                case 1 -> {
                    System.out.println("\nCADASTRAR DOADOR");
                    String nome = Entrada.lerTexto("Nome:");
                    String telefone = Entrada.lerTelefone("Telefone:");
                    String email = Entrada.lerEmail("Email:");
                    String endereco = Entrada.lerTexto("Endereço:");
                    sistema.cadastrarDoador(nome, telefone, email, endereco);
                }

                case 2 -> {
                    System.out.println("\nCADASTRAR BENEFICIÁRIO");
                    String nome = Entrada.lerTexto("Nome:");
                    String telefone = Entrada.lerTelefone("Telefone:");
                    String email = Entrada.lerEmail("Email:");
                    String endereco = Entrada.lerTexto("Endereço:");

                    System.out.println("Tipos disponíveis: FAMILIA, ONG, ESCOLA, ABRIGO");
                    TipoBeneficiario tipo = null;
                    while (tipo == null) {
                        String tipoStr = Entrada.lerTexto("Tipo:").toUpperCase();
                        try {
                            tipo = TipoBeneficiario.valueOf(tipoStr);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Tipo inválido! Use: FAMILIA, ONG, ESCOLA ou ABRIGO.");
                        }
                    }

                    int prioridade = Entrada.lerInt("Prioridade (1 a 5):");
                    while (Validacao.prioridadeInvalida(prioridade)) {
                        System.out.println("Prioridade inválida! Deve ser entre 1 e 5.");
                        prioridade = Entrada.lerInt("Prioridade (1 a 5):");
                    }

                    sistema.cadastrarBeneficiario(nome, telefone, email, endereco, tipo, prioridade);
                }

                case 3 -> {
                    System.out.println("\nCADASTRAR ITEM DE DOAÇÃO");
                    String nome = Entrada.lerTexto("Nome do item:");
                    String categoria = Entrada.lerTexto("Categoria:");
                    String descricao = Entrada.lerTexto("Descrição:");

                    int quantidade = Entrada.lerInt("Quantidade:");
                    while (Validacao.quantidadeInvalida(quantidade)) {
                        System.out.println("Quantidade inválida! Deve ser maior que zero.");
                        quantidade = Entrada.lerInt("Quantidade:");
                    }

                    String estado = Entrada.lerTexto("Estado de conservação:");
                    sistema.cadastrarItem(nome, categoria, descricao, quantidade, estado);
                }

                case 4 -> {
                    System.out.println("\nDOADORES CADASTRADOS");
                    var doadores = sistema.listarDoadores();
                    if (doadores.isEmpty()) {
                        System.out.println("Nenhum doador cadastrado.");
                    } else {
                        doadores.forEach(System.out::println);
                    }
                }

                case 5 -> {
                    System.out.println("\nBENEFICIÁRIOS CADASTRADOS");
                    var beneficiarios = sistema.listarBeneficiarios();
                    if (beneficiarios.isEmpty()) {
                        System.out.println("Nenhum beneficiário cadastrado.");
                    } else {
                        beneficiarios.stream().sorted().forEach(System.out::println);
                    }
                }

                case 6 -> {
                    System.out.println("\nITENS DE DOAÇÃO");
                    var itens = sistema.listarItens();
                    if (itens.isEmpty()) {
                        System.out.println("Nenhum item cadastrado.");
                    } else {
                        itens.forEach(System.out::println);
                    }
                }

                case 7 -> {
                    System.out.println("\nFILTRAR ITENS POR STATUS");
                    System.out.println("Status disponíveis: DISPONIVEL, RESERVADO, ENTREGUE, CANCELADO");
                    StatusItem status = null;
                    while (status == null) {
                        String statusStr = Entrada.lerTexto("Digite o status desejado:").toUpperCase();
                        try {
                            status = StatusItem.valueOf(statusStr);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Status inválido! Use: DISPONIVEL, RESERVADO, ENTREGUE ou CANCELADO.");
                        }
                    }
                    var itensFiltrados = sistema.listarItensPorStatus(status);
                    if (itensFiltrados.isEmpty()) {
                        System.out.println("Nenhum item encontrado com este status.");
                    } else {
                        itensFiltrados.forEach(System.out::println);
                    }
                }

                case 8 -> {
                    System.out.println("\nSOLICITAR ITEM");

                    var beneficiarios = sistema.listarBeneficiarios();
                    if (beneficiarios.isEmpty()) {
                        System.out.println("Nenhum beneficiário cadastrado.");
                        break;
                    }

                    var itens = sistema.listarItens();
                    if (itens.isEmpty()) {
                        System.out.println("Nenhum item cadastrado.");
                        break;
                    }

                    System.out.println("\nBeneficiários");
                    beneficiarios.forEach(System.out::println);

                    int idBeneficiario = Entrada.lerInt("ID do beneficiário:");

                    System.out.println("\nItens Disponíveis");
                    itens.forEach(System.out::println);

                    int idItem = Entrada.lerInt("ID do item:");
                    int quantidade = Entrada.lerInt("Quantidade:");
                    String justificativa = Entrada.lerTexto("Justificativa:");

                    sistema.solicitarItem(idBeneficiario, idItem, quantidade, justificativa);
                }

                case 9 -> {
                    System.out.println("\nSOLICITAÇÕES");
                    var solicitacoes = sistema.listarSolicitacoes();
                    if (solicitacoes.isEmpty()) {
                        System.out.println("Nenhuma solicitação registrada.");
                    } else {
                        solicitacoes.forEach(System.out::println);
                    }
                }

                case 10 -> {
                    System.out.println("\nAPROVAR SOLICITAÇÃO");

                    var solicitacoes = sistema.listarSolicitacoes();
                    if (solicitacoes.isEmpty()) {
                        System.out.println("Nenhuma solicitação registrada.");
                        break;
                    }

                    System.out.println("\nSolicitações");
                    solicitacoes.forEach(System.out::println);

                    int idSolicitacao = Entrada.lerInt("ID da solicitação a aprovar:");
                    sistema.aprovarSolicitacao(idSolicitacao);
                }

                case 11 -> {
                    System.out.println("\nCONCLUIR ENTREGA");

                    var solicitacoes = sistema.listarSolicitacoes();
                    if (solicitacoes.isEmpty()) {
                        System.out.println("Nenhuma solicitação registrada.");
                        break;
                    }

                    System.out.println("\nSolicitações");
                    solicitacoes.forEach(System.out::println);

                    int idSolicitacao = Entrada.lerInt("ID da solicitação aprovada:");

                    var doadores = sistema.listarDoadores();
                    if (doadores.isEmpty()) {
                        System.out.println("Nenhum doador cadastrado.");
                        break;
                    }

                    System.out.println("\nDoadores");
                    doadores.forEach(System.out::println);

                    int idDoador = Entrada.lerInt("ID do doador responsável:");
                    String observacoes = Entrada.lerTextoOpcional("Observações (ou Enter para pular):");

                    sistema.concluirEntrega(idSolicitacao, idDoador, observacoes);
                }

                case 12 -> {
                    System.out.println("\nRELATÓRIO DE DOAÇÕES EFETIVADAS");
                    var doacoes = sistema.gerarRelatorio();
                    if (doacoes.isEmpty()) {
                        System.out.println("Nenhuma doação efetivada registrada.");
                    } else {
                        doacoes.forEach(System.out::println);
                    }
                }

                case 13 -> {
                    System.out.println("\nREJEITAR/CANCELAR SOLICITAÇÃO");
                    var solicitacoes = sistema.listarSolicitacoes();
                    if (solicitacoes.isEmpty()) {
                        System.out.println("Nenhuma solicitação registrada.");
                        break;
                    }

                    System.out.println("\nSolicitações");
                    solicitacoes.forEach(System.out::println);

                    int idSolicitacao = Entrada.lerInt("ID da solicitação a rejeitar/cancelar:");
                    sistema.rejeitarSolicitacao(idSolicitacao);
                }

                case 0 -> {
                    System.out.println("Sistema encerrado. Obrigado por usar a Rede Solidária!");
                    DatabaseConnection.fecharConexao();
                    Entrada.fechar();
                }

                default -> System.out.println("Opção inválida! Tente novamente.");
            }

            if (opcao != 0) {
                Entrada.pausar();
            }

        } while (opcao != 0);
    }
}
