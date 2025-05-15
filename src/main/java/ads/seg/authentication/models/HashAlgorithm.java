package ads.seg.authentication.models;

/**
	Interface contratual para implementar alroritmo hash de escolha do desenvolvedor.
 */
public interface HashAlgorithm {
	/**
	 * Método para cifrar senhas.
	 *
	 * @param password String contendo a senha do usuário.
	 * @return String contendo a senha cifrada.
	 */
	String hash(String password);

	/**
	 * Método para checar se uma senha e um hash são correspondentes.
	 *
	 * @param password String contendo a senha do usuário.
	 * @param hash String contendo o hash.
	 * @return um valor booleano que indica se o os dois argumentos são identicos ou não.
	 */
	boolean check(String password, String hash);
}
