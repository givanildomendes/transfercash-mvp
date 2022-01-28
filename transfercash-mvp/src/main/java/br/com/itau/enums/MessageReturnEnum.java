package br.com.itau.enums;

public enum MessageReturnEnum {

	SUCESS(1, "Cadastro realizado com sucesso"),
	ERROR(99, "ATENCÃO!!! Cadastro NÃO foi realizado."),
	
	SUCESS_LIST(1, "Lista carregada com sucesso"),
	ERROR_LIST(99, "ATENCÃO!!! Lista NÃO foi carregada."),
	
	SUCESS_SEARCH(1, "Pesquisa realizada com sucesso"),
	ERROR_SEARCH(99, "ATENCÃO!!! Pesquisa NÃO foi realizada."),
	
	SUCESS_TRANSFER(1, "Transferência realizada com sucesso"),
	ERROR_TRANSFER(99, "ATENCÃO!!! Transferência NÃO foi realizada.");
	
	private Integer code;
	private String message;

	MessageReturnEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMensagem(String message) {
		this.message = message;
	}
}
