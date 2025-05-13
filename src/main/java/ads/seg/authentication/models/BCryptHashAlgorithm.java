package ads.seg.authentication.models;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHashAlgorithm implements HashAlgorithm {

	@Override
	public String hash(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@Override
	public boolean verify(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}

}
