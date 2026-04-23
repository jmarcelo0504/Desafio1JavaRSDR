import service.ServiceRSDR;
import java.util.Scanner;

public class main {

    static Scanner sc = new Scanner(System.in);
    static ServiceRSDR sistema = new ServiceRSDR();

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n=== SISTEMA DE DOAÇÃO ===");
            System.out.println("1 - Cadastrar Doador");
            System.out.println("2 - Cadastrar Beneficiário");
            System.out.println("3 - Cadastrar Item");
            System.out.println("4 - Listar Doadores");
            System.out.println("5 - Listar Beneficiários");
            System.out.println("6 - Listar Itens");
            System.out.println("0 - Sair");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarDoador();
                case 2 -> cadastrarBeneficiario();
                case 3 -> cadastrarItem();
                case 4 -> sistema.listarDoadores().forEach(System.out::println);
                case 5 -> sistema.listarBeneficiarios().forEach(System.out::println);
                case 6 -> sistema.listarItens().forEach(System.out::println);
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    static void cadastrarDoador() {
        System.out.println("Nome:");
        String nome = sc.nextLine();

        System.out.println("Telefone:");
        String tel = sc.nextLine();

        System.out.println("Email:");
        String email = sc.nextLine();

        System.out.println("Endereço:");
        String end = sc.nextLine();

        sistema.cadastrarDoador(nome, tel, email, end);
        System.out.println("Doador cadastrado!");
    }

    static void cadastrarBeneficiario() {
        System.out.println("Nome:");
        String nome = sc.nextLine();

        System.out.println("Telefone:");
        String tel = sc.nextLine();

        System.out.println("Email:");
        String email = sc.nextLine();

        System.out.println("Endereço:");
        String end = sc.nextLine();

        System.out.println("Tipo:");
        String tipo = sc.nextLine();

        System.out.println("Prioridade:");
        int prioridade = sc.nextInt();
        sc.nextLine();

        sistema.cadastrarBeneficiario(nome, tel, email, end, tipo, prioridade);
        System.out.println("Beneficiário cadastrado!");
    }

    static void cadastrarItem() {
        System.out.println("Nome:");
        String nome = sc.nextLine();

        System.out.println("Categoria:");
        String cat = sc.nextLine();

        System.out.println("Descrição:");
        String desc = sc.nextLine();

        System.out.println("Quantidade:");
        int qtd = sc.nextInt();
        sc.nextLine();

        System.out.println("Estado:");
        String estado = sc.nextLine();

        sistema.cadastrarItem(nome, cat, desc, qtd, estado);
        System.out.println("Item cadastrado!");
    }
}