package ads.seg.authentication.exceptions;

public class UserNotFoundException extends RuntimeException  {
	public UserNotFoundException(String message) {
		super(message);
	}
}
