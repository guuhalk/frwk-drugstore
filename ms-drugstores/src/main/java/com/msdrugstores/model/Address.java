package com.msdrugstores.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Embeddable
public class Address {

	@Column(name = "address_postal_code")
	private String postalCode;
	
	@Column(name = "address_street")
	private String street;
	
	@Column(name = "address_number")
	private String number;
	
	@Column(name = "address_neighborhood")
	private String neighborhood;
	
	@Column(name = "address_city")
	private String city;
	
	@Column(name = "address_state")
	private String state;
	
}
