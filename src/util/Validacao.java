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

    public static boolean emailValido(String email) {
        if (email == null) return false;
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    public static boolean telefoneValido(String telefone) {
        if (telefone == null) return false;
        if (!telefone.matches("^[+\\d\\s().-]+$")) {
            return false;
        }
        long digitos = telefone.chars().filter(Character::isDigit).count();
        return digitos >= 8 && digitos <= 15;
    }
}