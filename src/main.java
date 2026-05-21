import service.ServiceRSDR;
import util.Entrada;
import util.Menu;
import util.Validacao;

public class Main {

    public static void main(String[] args) {

        ServiceRSDR sistema = new ServiceRSDR();

        int opcao;

        do {

            Menu.exibirMenu();

            opcao = Entrada.lerInt("Escolha uma opção:");

            switch (opcao) {

                case 1 -> {

                    String nome = Entrada.lerTexto("Nome: ");
                    String telefone = Entrada.lerTexto("Telefone: ");
                    String email = Entrada.lerTexto("Email: ");
                    String endereco = Entrada.lerTexto("Endereço: ");
                    sistema.cadastrarDoador(nome, telefone, email, endereco);
                }

                case 2 -> {

                    String nome = Entrada.lerTexto("Nome:");
                    String telefone = Entrada.lerTexto("Telefone:");
                    String email = Entrada.lerTexto("Email:");
                    String endereco = Entrada.lerTexto("Endereço:");
                    String tipo = Entrada.lerTexto("Tipo:");
                    int prioridade = Entrada.lerInt("Prioridade:");

                    if (Validacao.prioridadeInvalida(prioridade)) {
                        System.out.println("Prioridade inválida.");
                        break;
                    }
                    sistema.cadastrarBeneficiario(nome, telefone, email, endereco, tipo, prioridade);
                }
                case 3 -> {

                    String nome = Entrada.lerTexto("Nome:");
                    String categoria = Entrada.lerTexto("Categoria:");
                    String descricao = Entrada.lerTexto("Descrição:");
                    int quantidade = Entrada.lerInt("Quantidade:");

                    if (Validacao.quantidadeInvalida(quantidade)) {
                        System.out.println("Quantidade inválida.");

                        break;
                    }
                    String estado = Entrada.lerTexto("Estado:");
                    sistema.cadastrarItem(nome, categoria, descricao, quantidade, estado);
                }

                case 4 ->
                        sistema.listarDoadores().forEach(System.out::println);

                case 5 ->
                        sistema.listarBeneficiarios().forEach(System.out::println);

                case 6 ->
                        sistema.listarItens().forEach(System.out::println);
                
                case 7 -> {

                    if (sistema.listarBeneficiarios().isEmpty()) {
                        System.out.println("Nenhum beneficiário cadastrado.");
                        break;
                    }

                    if (sistema.listarItens().isEmpty()) {
                        System.out.println("Nenhum item cadastrado.");
                        break;
                    }
                    System.out.println("\n=== BENEFICIÁRIOS ===");

                    for (int i = 0; i < sistema.listarBeneficiarios().size(); i++) {
                        System.out.println(i + " - " + sistema.listarBeneficiarios().get(i));
                    }

                    int beneficiario = Entrada.lerInt("Escolha o beneficiário: ");
                    System.out.println("\n=== ITENS ===");

                    for (int i = 0; i < sistema.listarItens().size(); i++) {

                        System.out.println(i + " - " + sistema.listarItens().get(i));
                    }

                    int item = Entrada.lerInt("Escolha o item: ");
                    int quantidade = Entrada.lerInt("Quantidade: ");

                    String justificativa = Entrada.lerTexto("Justificativa: ");
                    sistema.solicitarItem(beneficiario, item, quantidade, justificativa);
                }

                case 8 ->
                        sistema.listarSolicitacoes().forEach(System.out::println);

                case 0 ->
                        System.out.println("Sistema encerrado.");

                default ->
                        System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
}