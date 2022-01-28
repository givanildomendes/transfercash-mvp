package br.com.itau.exception;

public class CustomerIdAlreadyExistsException extends Exception {

	/** The serial version UID. */
	public static final long serialVersionUID = 19720926L;

	/**
	 * Construtor da classe
	 */
	public CustomerIdAlreadyExistsException() {
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param message
	 */
	public CustomerIdAlreadyExistsException(String message) {
		super(message);
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param message
	 * @param exception
	 */
	public CustomerIdAlreadyExistsException(String message, Throwable exception) {
		super(message, exception);
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param e
	 */
	public CustomerIdAlreadyExistsException(Exception e) {
		super(e);
	}
}