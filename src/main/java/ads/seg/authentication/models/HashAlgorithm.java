package ads.seg.authentication.models;

public interface HashAlgorithm {

	String hash(String password);

	boolean check(String password, String hash);
}
