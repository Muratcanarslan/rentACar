package com.rentACar.rentACar.api.models;

import javax.validation.Valid;

import com.rentACar.rentACar.business.requests.bankServiceRequests.CreateBankServiceRequest;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceListRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForCorporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentForCorporateCustomerModel {
	
	@Valid
	private CreateRentedCarRequestForCorporateCustomer createRentedCarRequestForCorporateCustomer;
	
	@Valid
	private CreateBankServiceRequest bankServiceRequest;
	
	@Valid
	private CreateOrderedAdditionalServiceListRequest createOrderedAdditionalServiceListRequests;

}
