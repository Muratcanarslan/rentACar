package com.rentACar.rentACar.business.requests.individualCustomerDtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {
	
	@Email
	@NotNull
	private String email;
	
	@NotNull
	@Size(min = 6,max=255)
	private String password;
	
	@NotNull
	@Size(min = 2,max=255)
	private String firstName;
	
	@NotNull
	@Size(min = 2,max=255)
	private String lastName;
	
	@NotNull
	@Size(min = 11,max=11)
	private String nationalIdentity;
}
