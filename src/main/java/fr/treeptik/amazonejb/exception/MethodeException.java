package fr.treeptik.amazonejb.exception;

public class MethodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MethodeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MethodeException(String message) {
		super(message);
	}

}
