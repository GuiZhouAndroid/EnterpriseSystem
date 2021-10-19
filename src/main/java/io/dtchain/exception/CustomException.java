package io.dtchain.exception;

public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String msg;
	private String code;
	public CustomException(String msg, String code) {
		super();
		this.msg = msg;
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
