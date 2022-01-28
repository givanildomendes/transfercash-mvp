package br.com.itau.exception;

public class BusinessRuleException extends Exception {

	/** The serial version UID. */
	public static final long serialVersionUID = 19720926L;

	/**
	 * Construtor da classe
	 */
	public BusinessRuleException() {
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param message
	 */
	public BusinessRuleException(String message) {
		super(message);
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param message
	 * @param exception
	 */
	public BusinessRuleException(String message, Throwable exception) {
		super(message, exception);
	}

	/**
	 * Construtor da Superclasse
	 * 
	 * @param e
	 */
	public BusinessRuleException(Exception e) {
		super(e);
	}
}