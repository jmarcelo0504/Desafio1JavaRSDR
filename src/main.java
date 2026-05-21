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

                case 0 ->
                        System.out.println("Sistema encerrado.");
            }

        } while (opcao != 0);
    }
}