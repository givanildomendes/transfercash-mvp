package br.com.itau.exception;

public class FailedException extends Exception {

	/** The serial version UID. */
	public static final long serialVersionUID = 19720926L;

	/**
	 * Construtor da classe
	 */
	public FailedException() {
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param message
	 */
	public FailedException(String message) {
		super(message);
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param message
	 * @param exception
	 */
	public FailedException(String message, Throwable exception) {
		super(message, exception);
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param e
	 */
	public FailedException(Exception e) {
		super(e);
	}
}