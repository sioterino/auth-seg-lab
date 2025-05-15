package ads.seg.authentication.models;

import ads.seg.authentication.exceptions.*;

/**
 * Serviço responsável pelas operações de registro, autenticação e edição de senha.
 */
public class UserService {

	/** Repositório de usuários utilizado para persistência. */
	private final UserRepository repository;

	/** Algoritmo de hash utilizado para proteger senhas. */
	private final HashAlgorithm hashAlgorithm;

	/**
	 * Construtor da classe UserService.
	 *
	 * @param repository Repositório de usuários.
	 * @param hashAlgorithm Algoritmo de hash utilizado para senhas.
	 */
	public UserService(UserRepository repository, HashAlgorithm hashAlgorithm) {
		this.repository = repository;
		this.hashAlgorithm = hashAlgorithm;
	}

	/**
	 * Registra um novo usuário no sistema.
	 *
	 * @param login Nome de login do usuário.
	 * @param password Senha a ser cifrada.
	 *
	 * @throws UserAlreadyExistsException Caso o nome de login já existir.
	 */
	public void register(String login, String password) {
		if (repository.findByLogin(login) != null) {
			throw new UserAlreadyExistsException("Usuário já existe.");
		}

		String hashed = hashAlgorithm.hash(password);
		repository.save(new User(login, hashed));
	}

	/**
	 * Autentica um usuário com login e senha.
	 *
	 * @param login Nome de login do usuário.
	 * @param password Senha não cifrada a ser comparada.
	 *
	 * @throws InvalidLoginException Se o login não existir ou a senha estiver incorreta.
	 */
	public void authenticate(String login, String password) {
		User user = repository.findByLogin(login);

		if (user == null || !hashAlgorithm.check(password, user.getPassword())) {
			throw new InvalidLoginException("Login ou senha inválidos.");
		}
	}

	/**
	 * Edita a senha de um usuário existente.
	 *
	 * @param login Nome de login do usuário.
	 * @param currentPassword Senha atual do usuário.
	 * @param newPassword Nova senha a ser definida.
	 *
	 * @throws UserNotFoundException Se o usuário não for encontrado.
	 * @throws InvalidPasswordException Se a senha atual estiver incorreta.
	 */
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
