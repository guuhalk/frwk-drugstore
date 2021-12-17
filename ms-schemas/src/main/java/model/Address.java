package model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address implements Serializable {

	private String postalCode;
	
	private String street;
	
	private String number;
	
	private String neighborhood;
	
	private String city;
	
	private String state;
	
}
