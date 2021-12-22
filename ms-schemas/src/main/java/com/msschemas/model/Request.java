package com.msschemas.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nameRequest;
	private Object body;
	private List<String> pathVariables;
	
	public Request(String nameRequest, Object body, List<String> pathVariables) {
		this.nameRequest = nameRequest;
		this.body = body;
		this.pathVariables = pathVariables;
	}
	
	public Request(String nameRequest, Object body) {
		this.nameRequest = nameRequest;
		this.body = body;
	}

	public Request(String nameRequest) {
		this.nameRequest = nameRequest;
	}

	public Request(String nameRequest, List<String> pathVariables) {
		this.nameRequest = nameRequest;
		this.pathVariables = pathVariables;
	}
	
}
