package ads.seg;

import ads.seg.authentication.models.UserService;
import ads.seg.authentication.models.HashAlgorithm;
import ads.seg.authentication.models.BCryptHashAlgorithm;
import ads.seg.authentication.models.UserRepository;
import ads.seg.authentication.models.FileUserRepository;

import java.io.Console;
import java.util.Scanner;

/**
 * Classe de entrada que fornece uma interface de linha de comando para registrar, autenticar e editar senhas de usuários.
 */
public class Main {

	/** Repositório de usuários > persistência em arquivo de texto no diretório do projeto. */
	static UserRepository repository = new FileUserRepository();

	/** Algoritmo de hash para senhas > BCrypt. */
	static HashAlgorithm hash = new BCryptHashAlgorithm();

	/** Serviço de usuário que gerencia as operações de registro, autenticação e edição de senha. */
	static UserService service = new UserService(repository, hash);

	/** Scanner para leitura dos inputs do usuário no terminal. */
	static Scanner scanner = new Scanner(System.in);

	/**
	 * Ponto de entrada da aplicação.
	 *
	 * Exibe um menu no console para o usuário escolher operações de registrar,
	 * autenticar, editar senha ou sair.
	 */
    public static void main(String[] args) {
		boolean run = true;

		while (run) {
			System.out.println("""
				\n
				================
				1. Registrar
				2. Autenticar
				3. Editar Senha
				0. Sair
				=================
				\n
				""");

			System.out.print("> ");
			int option = scanner.nextInt();
			scanner.nextLine();

			try {
				switch (option) {
					case 0:
						System.out.println("\nDesligando...");
						run = false;
						break;
					case 1: registerUser(); break;
					case 2: authenticateUser(); break;
					case 3: editPassword(); break;
					default: System.out.println("\nOpção inválida.");
				}
			} catch (RuntimeException e) {
				System.err.println("Erro: " + e.getMessage());
			}
		}

		scanner.close();
	}

	/**
	 * Lê o input de nome de login do usuário via terminal.
	 *
	 * @return Nome de login digitado pelo usuário.
	 */
	private static String getLoginInput() {
		System.out.print("Login: ");
		return scanner.nextLine();
	}

	/**
	 * Lê o input da senha do usuário via terminal.
	 *
	 * @param mensagem String a ser exibida na tela.
	 * @return Senha digitada pelo usuário.
	 */
	private static String getPasswordInput(String mensagem) {
		System.out.print(mensagem);
		return scanner.nextLine();
	}

	/**
	 * Realiza o registro de um novo usuário.
	 *
	 * Pede por nome de login e senha, e chama o UserService para registrar.
	 */
	private static void registerUser() {

		String login = getLoginInput();
		String senha = getPasswordInput("Senha: ");

		service.register(login, senha);
		System.out.println("Usuário registrado!");
	}

	/**
	 * Realiza a autenticação do usuário.
	 *
	 * Pede por nome de login e senha, e chama o UserService para autenticar.
	 */
	private static void authenticateUser() {

		String login = getLoginInput();
		String senha = getPasswordInput("Senha: ");

		service.authenticate(login, senha);
		System.out.println("Login realizado com sucesso!");
	}

	/**
	 * Realiza a edição de senha do usuário.
	 *
	 * Pede por nome de login, senha atual e nova senha, e chama o UserService para editar a senha.
	 */
	private static void editPassword() {

		String login = getLoginInput();
		String atual = getPasswordInput("Senha atual: ");
		String nova = getPasswordInput("Senha nova: ");

		service.updatePassword(login, atual, nova);
		System.out.println("Senha atualizada.");
	}
}