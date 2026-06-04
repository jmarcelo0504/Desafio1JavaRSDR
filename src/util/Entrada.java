package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Entrada {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * @param mensagem a mensagem a ser exibida ao usuário
     * @return o valor inteiro lido
     */
    public static int lerInt(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem + " ");
                int valor = sc.nextInt();
                sc.nextLine(); // Limpa o buffer
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite apenas números inteiros.");
                sc.nextLine(); // Limpa o buffer inválido
            }
        }
    }

    /**
     * @param mensagem a mensagem a ser exibida ao usuário
     * @return o texto lido (nunca vazio)
     */
    public static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem + " ");
            String texto = sc.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Entrada inválida! O campo não pode ser vazio.");
        }
    }

    /**
     * @param mensagem a mensagem a ser exibida ao usuário
     * @return o texto lido (pode ser vazio)
     */
    public static String lerTextoOpcional(String mensagem) {
        System.out.print(mensagem + " ");
        return sc.nextLine().trim();
    }

    public static String lerTelefone(String mensagem) {
        while (true) {
            String telefone = lerTexto(mensagem);
            if (Validacao.telefoneValido(telefone)) {
                return telefone;
            }
            System.out.println("Telefone inválido! Digite apenas números, parênteses, espaço ou hífen (mínimo de 8 dígitos).");
        }
    }

    public static String lerEmail(String mensagem) {
        while (true) {
            String email = lerTexto(mensagem);
            if (Validacao.emailValido(email)) {
                return email;
            }
            System.out.println("E-mail inválido! Digite um e-mail no formato correto (ex: nome@dominio.com).");
        }
    }

    public static void pausar() {
        System.out.println("\nPressione ENTER para voltar ao menu principal...");
        sc.nextLine();
    }

    public static void fechar() {
        sc.close();
    }
}