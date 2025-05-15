package ads.seg.authentication.models;

/**
 * Interface que estabele contrato para implementação de um algoritmo que armazene dados de um User usuário.
 */
public interface UserRepository {
	/**
	 * Método que salva dados de um usuário.
	 *
	 * @param user Objeto com os dados do usuário.
	 */
	void save(User user);

	/**
	 * Método para atualizar dados já salvos de um usuário.
	 *
	 * @param user Objeto com os dados do usuário.
	 */
	void update(User user);

	/**
	 * Método que procura um usuário armazenado.
	 *
	 * @param login Nome de login do usuário a ser pesquisado.
	 * @return Objeto User com os dados do usuário encontrado.
	 */
	User findByLogin(String login);
}
