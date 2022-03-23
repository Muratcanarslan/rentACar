package com.rentACar.rentACar.api.models;


import com.rentACar.rentACar.business.requests.bankServiceRequests.CreateBankServiceRequest;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceListRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForIndividualCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentForIndividualCustomerModel {
	
	private CreateRentedCarRequestForIndividualCustomer createRentedCarRequestForIndividualCustomer;
	
	private CreateOrderedAdditionalServiceListRequest createOrderedAdditionalServiceListRequests;
	
	private CreateBankServiceRequest createBankServiceRequest;

}
