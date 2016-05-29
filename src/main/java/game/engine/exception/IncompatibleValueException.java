package game.engine.exception;

/**
 * A value, that is not compatible was used.
 * 
 * @author Marvin Bruns
 *
 */
public class IncompatibleValueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IncompatibleValueException() {
		super();
	}

	public IncompatibleValueException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IncompatibleValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncompatibleValueException(String message) {
		super(message);
	}

	public IncompatibleValueException(Throwable cause) {
		super(cause);
	}

}
