package model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Request implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nameRequest;
	private Object body;
	
}
