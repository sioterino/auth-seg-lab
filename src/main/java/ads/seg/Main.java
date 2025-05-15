package ads.seg;

import ads.seg.authentication.models.UserService;
import ads.seg.authentication.models.HashAlgorithm;
import ads.seg.authentication.models.BCryptHashAlgorithm;
import ads.seg.authentication.models.UserRepository;
import ads.seg.authentication.models.FileUserRepository;

import java.io.Console;
import java.util.Scanner;

public class Main {

	static UserRepository repository = new FileUserRepository();

	static HashAlgorithm hash = new BCryptHashAlgorithm();

	static UserService service = new UserService(repository, hash);

	static Scanner scanner = new Scanner(System.in);

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

	private static String getLoginInput() {
		System.out.print("Login: ");
		return scanner.nextLine();
	}

	private static String getPasswordInput(String mensagem) {
		System.out.print(mensagem);
		return scanner.nextLine();
	}

	private static void registerUser() {

		String login = getLoginInput();
		String senha = getPasswordInput("Senha: ");

		service.register(login, senha);
		System.out.println("Usuário registrado!");
	}

	private static void authenticateUser() {

		String login = getLoginInput();
		String senha = getPasswordInput("Senha: ");

		service.authenticate(login, senha);
		System.out.println("Login realizado com sucesso!");
	}

	private static void editPassword() {

		String login = getLoginInput();
		String atual = getPasswordInput("Senha atual: ");
		String nova = getPasswordInput("Senha nova: ");

		service.updatePassword(login, atual, nova);
		System.out.println("Senha atualizada.");
	}
}