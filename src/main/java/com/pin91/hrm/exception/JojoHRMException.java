package com.pin91.hrm.exception;

public class JojoHRMException extends RuntimeException {

	private static final long serialVersionUID = 28267421607369293L;

	private String errorCode;
	private String errorMessage;

	public JojoHRMException() {
		super();
	}

	public JojoHRMException(String message) {
		super(message);
		this.errorMessage = message;
	}

	public JojoHRMException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.errorMessage = message;
	}

	public JojoHRMException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.errorMessage = message;
	}

	@Override
	public String getMessage() {

		return errorCode + " : " + errorMessage;
	}
}
