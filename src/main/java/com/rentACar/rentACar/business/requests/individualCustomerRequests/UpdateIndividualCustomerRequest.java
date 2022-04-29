package com.rentACar.rentACar.business.requests.individualCustomerRequests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	
	@NotNull
	@Positive
	private int individualCustomerId;
	
	@Email
	@NotNull
	private String email;
	
	@NotNull
	@Size(min = 6,max=255)
	private String password;
	
	@NotNull
	@Size(min = 2,max=255)
	@Pattern(regexp = "^[a-zA-Z]+$")
	private String firstName;
	
	@NotNull
	@Pattern(regexp = "^[a-zA-Z]{2,255}")
	private String lastName;
	
	@NotNull
	@Pattern(regexp = "^[0-9]{11}")
	private String nationalIdentity;
}
