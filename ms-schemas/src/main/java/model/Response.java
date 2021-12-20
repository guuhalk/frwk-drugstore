package model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	private Object object;
	private List<Object> listObject;
	
}
