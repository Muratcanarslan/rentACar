package com.rentACar.rentACar.api.models;

import com.rentACar.rentACar.business.requests.bankServiceRequests.CreateBankServiceRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForCorporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentedCarForCorporateCustomerAndBankServiceModel {
	
	private CreateRentedCarRequestForCorporateCustomer createRentedCarRequestForCorporateCustomer;
	
	private CreateBankServiceRequest createBankServiceRequest;

}
