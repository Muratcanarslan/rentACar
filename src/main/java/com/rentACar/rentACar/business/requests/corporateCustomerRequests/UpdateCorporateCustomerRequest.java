package com.rentACar.rentACar.business.requests.corporateCustomerRequests;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	
	@NotNull
	@Min(0)
	private int corporateCustomerId;

	@Email
	@NotNull
	private String email;

	@NotNull
	@Size(min = 6, max = 255)
	private String password;

	@NotNull
	@Size(min = 2, max = 255)
	private String companyName;

	@NotNull
	@Pattern(regexp = "^[0-9]{10}")
	private String taxNumber;

}
