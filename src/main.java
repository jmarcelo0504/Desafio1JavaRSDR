import service.ServiceRSDR;
import java.util.Scanner;

public class main {

    static Scanner scanner = new Scanner(System.in);
    static ServiceRSDR sistema = new ServiceRSDR();

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\nSISTEMA DE DOAÇÃO");
            System.out.println("1 - Cadastrar Doador");
            System.out.println("2 - Cadastrar Beneficiário");
            System.out.println("3 - Cadastrar Item");
            System.out.println("4 - Listar Doadores");
            System.out.println("5 - Listar Beneficiários");
            System.out.println("6 - Listar Itens");
            System.out.println("0 - Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarDoador();
                case 2 -> cadastrarBeneficiario();
                case 3 -> cadastrarItem();
                case 4 -> sistema.listarDoadores().forEach(System.out::println);
                case 5 -> sistema.listarBeneficiarios().forEach(System.out::println);
                case 6 -> sistema.listarItens().forEach(System.out::println);
                case 0 -> System.out.println("Saindo do programa!");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    static void cadastrarDoador() {
        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("Telefone:");
        String telefone = scanner.nextLine();

        System.out.println("Email:");
        String email = scanner.nextLine();

        System.out.println("Endereço:");
        String endereco = scanner.nextLine();

        sistema.cadastrarDoador(nome, telefone, email, endereco);
        System.out.println("Doador cadastrado!");
    }

    static void cadastrarBeneficiario() {
        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("Telefone:");
        String telefone = scanner.nextLine();

        System.out.println("Email:");
        String email = scanner.nextLine();

        System.out.println("Endereço:");
        String endereco = scanner.nextLine();

        System.out.println("Tipo:");
        String tipo = scanner.nextLine();

        System.out.println("Prioridade:");
        int prioridade = scanner.nextInt();
        scanner.nextLine();

        sistema.cadastrarBeneficiario(nome, telefone, email, endereco, tipo, prioridade);
        System.out.println("Beneficiário cadastrado!");
    }

    static void cadastrarItem() {
        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("Categoria:");
        String categoria = scanner.nextLine();

        System.out.println("Descrição:");
        String descricao = scanner.nextLine();

        System.out.println("Quantidade:");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Estado:");
        String estado = scanner.nextLine();

        sistema.cadastrarItem(nome, categoria, descricao, quantidade, estado);
        System.out.println("Item cadastrado!");
    }
}