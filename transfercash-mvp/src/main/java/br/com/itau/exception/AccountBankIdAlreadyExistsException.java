package br.com.itau.exception;

public class AccountBankIdAlreadyExistsException extends Exception {

	/** The serial version UID. */
	public static final long serialVersionUID = 19720926L;

	/**
	 * Construtor da classe
	 */
	public AccountBankIdAlreadyExistsException() {
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param message
	 */
	public AccountBankIdAlreadyExistsException(String message) {
		super(message);
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param message
	 * @param exception
	 */
	public AccountBankIdAlreadyExistsException(String message, Throwable exception) {
		super(message, exception);
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param e
	 */
	public AccountBankIdAlreadyExistsException(Exception e) {
		super(e);
	}
}