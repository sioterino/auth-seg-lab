package ads.seg.authentication.models;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileUserRepository implements UserRepository {

	private final Map<String, User> users = new HashMap<>();
	private final File file = new File("users.txt");


	public FileUserRepository() {
		loadFromFile();
	}


	private Scanner scanFile() {
		try {
			return new Scanner(new FileReader(file));

		} catch (FileNotFoundException e) {
			throw new RuntimeException("Erro ao carregar os usuários: " + e.getMessage(), e);
		}
	}


	private PrintWriter newPrintWriter() {
		try {
			return new PrintWriter(new FileWriter(file));

		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar os usuários: " + e.getMessage(), e);
		}
	}


	private void loadFromFile() {
		if (!file.exists()) return;

		Scanner leitor = scanFile();

		while (leitor.hasNext()) {

			String line = leitor.nextLine();
			String[] credentials = line.split(":", 2);

			if (credentials.length == 2) {
				String login = credentials[0];
				String password = credentials[1];
				users.put(login, new User(login, password));
			}

		}
	}


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
