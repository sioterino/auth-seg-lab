package ads.seg.authentication.exceptions;

public class InvalidPasswordException extends RuntimeException  {
	public InvalidPasswordException(String message) {
		super(message);
	}
}
