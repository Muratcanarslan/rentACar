package com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {
	
	private int orderedAdditionalServiceId;
	
	private int rentedCarId;
	
	private int additionalServiceId;
}
