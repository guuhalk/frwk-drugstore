package com.msuser.exception;

public class EntityInUseException extends GenericException {

	private static final long serialVersionUID = 1L;

	public EntityInUseException(String message) {
		super(message);
	}
}
