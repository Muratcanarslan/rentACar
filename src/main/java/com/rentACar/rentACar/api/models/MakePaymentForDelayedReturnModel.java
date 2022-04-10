package com.rentACar.rentACar.api.models;

import javax.validation.Valid;

import com.rentACar.rentACar.business.requests.bankServiceRequests.CreateBankServiceRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarForDelayedReturnRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentForDelayedReturnModel {
	
	@Valid
	private UpdateRentedCarForDelayedReturnRequest updateRentedCarForDelayedReturnRequest;
	
	@Valid
	private CreateBankServiceRequest createBankServiceRequest;
	
}
