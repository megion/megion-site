package com.megion.site.core.exception;

public class MegionsiteException extends RuntimeException {

	private static final long serialVersionUID = 7840469006428735383L;

	public MegionsiteException(String message) {
		super(message);
	}
	
	public MegionsiteException(String message, Throwable cause) {
		super(message, cause);
	}

}
