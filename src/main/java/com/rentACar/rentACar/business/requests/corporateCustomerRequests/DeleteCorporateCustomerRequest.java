package com.rentACar.rentACar.business.requests.corporateCustomerRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCorporateCustomerRequest {
	
	@NotNull
	@Positive
	private int corporateCustomerId;
	

}
