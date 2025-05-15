package ads.seg.authentication.models;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe que implementa um algoritmo de cifragem utilizando a biblioteca BCrypt.
 */
public class BCryptHashAlgorithm implements HashAlgorithm {

	@Override
	public String hash(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(10)); // custo de hash 2^10
	}

	@Override
	public boolean check(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}

}
