package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

	private String postalCode;
	
	private String street;
	
	private String number;
	
	private String neighborhood;
	
	private String city;
	
	private String state;
}
