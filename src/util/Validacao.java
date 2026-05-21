package util;

public class Validacao {

    public static boolean textoVazio(String texto) {

        return texto == null || texto.isBlank();
    }

    public static boolean quantidadeInvalida(int valor) {

        return valor <= 0;
    }

    public static boolean prioridadeInvalida(int valor) {

        return valor < 1 || valor > 5;
    }
}