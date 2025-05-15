package ads.seg.authentication.models;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe que implementa algoritmos de armazenamento de dados de usuários User.
 */
public class FileUserRepository implements UserRepository {

	/** Estrutura HashMap onde os usuários e seus dados são armazenados antes de serem salvos em um arquivo de texto. */
	private final Map<String, User> users = new HashMap<>();
	/** Arquivo File com o endereço de onde os usuários devem ser salvos dentro do projeto. */
	private final File file = new File("users.txt");

	/** Construtor que carrega automaticamente os usuários já armazenados para dentro da estrutura de dados HashMap */
	public FileUserRepository() {
		loadFromFile();
	}

	/**
	 * Método que cria um Scanner para ler o arquivo de usuários salvos.
	 *
	 * @return Scanner para leitura do arquivo de texto.
	 *
	 * @throws RuntimeException caso o arquivo não seja encontrado.
	 */
	private Scanner scanFile() {
		try {
			return new Scanner(new FileReader(file));

		} catch (FileNotFoundException e) {
			throw new RuntimeException("Erro ao carregar os usuários: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que cria um Writer para sobrescrever o arquivo de usuários salvos.
	 *
	 * @return PrintWriter para escrita no arquivo de texto.
	 *
	 * @throws RuntimeException caso ocorra um erro ao abrir o arquivo para escrita.
	 */
	private PrintWriter newPrintWriter() {
		try {
			return new PrintWriter(new FileWriter(file));

		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar os usuários: " + e.getMessage(), e);
		}
	}

	/**
	 * Método que lê e converte os dados do arquivo de usuários salvos em
	 * um objeto User e os armazena na estrutura HashMap para serem manipulados.
	 *
	 * Caso o arquivo não exista no diretório, retorna sem carregar o HashMap.
	 */
	private void loadFromFile() {
		if (!file.exists()) return;

		Scanner leitor = scanFile();

		while (leitor.hasNext()) {

			String line = leitor.nextLine();
			String[] credentials = line.split(":", 2);

			String login = credentials[0];
			String password = credentials[1];

			users.put(login, new User(login, password));
		}

	}

	/**
	 * Método que pega os usuários armazenados no HashMap e os sobreescreve no arquivo de usuários salvos.
	 *
	 * Segue o seguinte padrão de armazenamento de dados "[login]:[senha cifrada]\n".
	 */
	private void saveToFile() {

		PrintWriter writer = newPrintWriter();

		for (User user : users.values()) {
			writer.println(user.getLogin() + ":" + user.getPassword());
		}

		writer.close();
	}


	@Override
	public void save(User user) {
		users.put(user.getLogin(), user);
		saveToFile();
	}


	@Override
	public void update(User user) {
		users.put(user.getLogin(), user);
		saveToFile();
	}


	@Override
	public User findByLogin(String login) {
		return users.get(login);
	}
}
