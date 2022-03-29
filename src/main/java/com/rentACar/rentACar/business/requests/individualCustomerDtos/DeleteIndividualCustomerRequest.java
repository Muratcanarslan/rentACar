package com.rentACar.rentACar.business.requests.individualCustomerDtos;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteIndividualCustomerRequest {
	
	@Positive
	@NotNull
	private int individualCustomerId;
	
}
