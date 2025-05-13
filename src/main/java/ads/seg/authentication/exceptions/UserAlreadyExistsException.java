package ads.seg.authentication.exceptions;

public class UserAlreadyExistsException extends RuntimeException  {
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
