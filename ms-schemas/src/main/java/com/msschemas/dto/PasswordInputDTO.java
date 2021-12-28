package com.msschemas.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordInputDTO {

	@NotBlank
	private String currentPassword;
	
	@NotBlank
	private String newPassword;
}
