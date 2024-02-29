package principal;

import java.util.List;
import java.util.Scanner;

import dao.UsuarioDAO;
import usuario.Usuario;

public class Principal {
    
    private static Scanner scanner = new Scanner(System.in);
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n==== Menu ====");
            System.out.println("1) Listar");
            System.out.println("2) Inserir");
            System.out.println("3) Excluir");
            System.out.println("4) Atualizar");
            System.out.println("5) Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    inserirUsuario();
                    break;
                case 3:
                    excluirUsuario();
                    break;
                case 4:
                    atualizarUsuario();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, escolha novamente.");
                    break;
            }
        } while (opcao != 5);
        
        scanner.close();
    }

    private static void listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioDAO.get();
            System.out.println("\n==== Lista de Usuários ====");
            for (Usuario u : usuarios) {
                System.out.println(u.toString());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
    }

    private static void inserirUsuario() {
        try {
            System.out.println("\n==== Inserir Usuário ====");
            System.out.print("Código: ");
            int codigo = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            System.out.print("Sexo (M/F): ");
            char sexo = scanner.nextLine().charAt(0);
            
            Usuario usuario = new Usuario(codigo, login, senha, sexo);
            if (usuarioDAO.insert(usuario)) {
                System.out.println("Usuário inserido com sucesso.");
            } else {
                System.out.println("Falha ao inserir usuário.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    private static void excluirUsuario() {
        try {
            System.out.println("\n==== Excluir Usuário ====");
            System.out.print("Digite o código do usuário a ser excluído: ");
            int codigo = scanner.nextInt();
            if (usuarioDAO.delete(codigo)) {
                System.out.println("Usuário excluído com sucesso.");
            } else {
                System.out.println("Usuário não encontrado ou falha ao excluir.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    private static void atualizarUsuario() {
        try {
            System.out.println("\n==== Atualizar Usuário ====");
            System.out.print("Digite o código do usuário a ser atualizado: ");
            int codigo = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha
            System.out.print("Novo login: ");
            String novoLogin = scanner.nextLine();
            System.out.print("Nova senha: ");
            String novaSenha = scanner.nextLine();
            System.out.print("Novo sexo (M/F): ");
            char novoSexo = scanner.nextLine().charAt(0);
            
            Usuario usuario = new Usuario(codigo, novoLogin, novaSenha, novoSexo);
            if (usuarioDAO.update(usuario)) {
                System.out.println("Usuário atualizado com sucesso.");
            } else {
                System.out.println("Usuário não encontrado ou falha ao atualizar.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }
}
