package com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequest {
	
	private int rentedCarId;
	
	private int additionalServiceId;
}
