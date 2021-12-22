package com.msschemas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithPassWordDTO extends UserDTO {

	private static final long serialVersionUID = 1L;

	private String password;
}
