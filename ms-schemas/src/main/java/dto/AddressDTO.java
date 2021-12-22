package dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String postalCode;
	
	private String street;
	
	private String number;
	
	private String neighborhood;
	
	private String city;
	
	private String state;
}
