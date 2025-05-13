package ads.seg.authentication.models;

public interface HashAlgorithm {
	String hash(String password);
	boolean verify(String password, String hash);
}
