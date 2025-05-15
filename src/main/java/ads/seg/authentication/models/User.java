package ads.seg.authentication.models;

/**
 * Classe que representa um usuário do sistema.
 * Contém as informações de login e a senha cifrada.
 */
public class User {

	/** String contendo o nome de login de um usuário. */
	private String login;

	/** String contendo a senha cifrada de um usuário. */
	private String password;

	/**
	 * Construtor da classe User.
	 *
	 * @param login Nome de login do usuário.
	 * @param password Senha já cifrada do usuário.
	 */
	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}

	/**
	 * Retorna o login do usuário.
	 *
	 * @return Nome de login do usuário.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Retorna a senha cifrada do usuário.
	 *
	 * @return Senha cifrada.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Atualiza a senha cifrada do usuário.
	 *
	 * @param password Nova senha já cifrada.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
