package br.com.itau.dto;

public class ResponseDTO {
	
	private Object data;
	private String message;
	private long code;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ResponseDTO [data=" + data + ", message=" + message + ", code=" + code + "]";
	}	
}
