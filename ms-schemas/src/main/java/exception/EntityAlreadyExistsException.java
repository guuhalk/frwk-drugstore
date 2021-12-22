package exception;

public class EntityAlreadyExistsException extends GenericException {

	private static final long serialVersionUID = 1L;

	public EntityAlreadyExistsException(String message) {
		super(message);
	}
}
