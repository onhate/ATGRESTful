package com.texelz.atgrestful.exceptions;

public class UnknownResourceException extends RuntimeException {

	private static final long serialVersionUID = -3867718498690715709L;

	public UnknownResourceException() {
		super(); // To change body of overridden methods use File | Settings |
					// File Templates.
	}

	public UnknownResourceException(String s) {
		super(s); // To change body of overridden methods use File | Settings |
					// File Templates.
	}

	public UnknownResourceException(String s, Throwable throwable) {
		super(s, throwable); // To change body of overridden methods use File |
								// Settings | File Templates.
	}

	public UnknownResourceException(Throwable throwable) {
		super(throwable); // To change body of overridden methods use File |
							// Settings | File Templates.
	}
}
