package com.msinventory.exceptionHandler;

public enum ProblemType {

	INVALID_DATA("/invalid-data", "Invalid data"),
	SYSTEM_ERROR("/system-error", "System error"),
	INVALID_PAREMETER("/invalid-parameter", "Invalid parameter"),
	INCOMPREHENSIBLE_MESSAGE("/inconprehensible-message", "Inconprehensible message"),
	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
	ENTITY_IN_USE("/entity-in-use", "Entity in use"),
	ENTITY_ALREADY_EXISTS("/entity-already-exists", "Entity already exists"),
	GENERIC_ERROR("/generic-error", "Generic rule violation");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "http://localhost:8080" + path;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getUri() {
		return uri;
	}
}
