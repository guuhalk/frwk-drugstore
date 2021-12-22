package com.msschemas.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;
	private Object body;
	private Integer responseCode;
}
