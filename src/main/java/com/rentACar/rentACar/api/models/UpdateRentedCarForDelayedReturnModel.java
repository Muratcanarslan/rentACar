package com.rentACar.rentACar.api.models;

import com.rentACar.rentACar.business.requests.bankServiceRequests.CreateBankServiceRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarForDelayedReturnRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentedCarForDelayedReturnModel {
	
	private UpdateRentedCarForDelayedReturnRequest updateRentedCarForDelayedReturnRequest;
	
	private CreateBankServiceRequest createBankServiceRequest;
}
