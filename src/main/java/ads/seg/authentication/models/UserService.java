package ads.seg.authentication.models;

import ads.seg.authentication.exceptions.*;

public class UserService {

	private final UserRepository repository;

	private final HashAlgorithm hashAlgorithm;

	public UserService(UserRepository repository, HashAlgorithm hashAlgorithm) {
		this.repository = repository;
		this.hashAlgorithm = hashAlgorithm;
	}

	public void register(String login, String password) {
		if (repository.findByLogin(login) != null) {
			throw new UserAlreadyExistsException("Usuário já existe.");
		}

		String hashed = hashAlgorithm.hash(password);
		repository.save(new User(login, hashed));
	}

	public void authenticate(String login, String password) {
		User user = repository.findByLogin(login);

		if (user == null || !hashAlgorithm.check(password, user.getPassword())) {
			throw new InvalidLoginException("Login ou senha inválidos.");
		}
	}

	public void updatePassword(String login, String currentPassword, String newPassword) {
		User user = repository.findByLogin(login);

		if (user == null) {
			throw new UserNotFoundException("Usuário ou senha incorretos.");
		}

		if (!hashAlgorithm.check(currentPassword, user.getPassword())) {
			throw new InvalidPasswordException("Usuário ou senha incorretos.");
		}

		user.setPassword(hashAlgorithm.hash(newPassword));
		repository.update(user);
	}
}
