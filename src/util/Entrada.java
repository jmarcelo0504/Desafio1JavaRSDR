package util;

import java.util.Scanner;

public class Entrada {

    private static Scanner sc = new Scanner(System.in);

    public static int lerInt(String mensagem) {
        while (true) {
            try {
                System.out.println(mensagem);
                int valor = sc.nextInt();
                sc.nextLine();
                
                return valor;
            } catch (Exception e) {
                System.out.println("Digite apenas numeros!");
                sc.nextLine();
            }
        }
    }

    public static String lerTexto(String mensagem) {
        System.out.println(mensagem);
        return sc.nextLine();
    }
}