package com.luminous.mpartner.service;

public class CustomException extends Exception {
	/**
	 * Added Serial ID to be used further.
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public CustomException() {
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}