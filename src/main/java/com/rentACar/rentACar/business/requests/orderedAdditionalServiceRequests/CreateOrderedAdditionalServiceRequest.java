package com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequest {
	
	@NotNull
	@Positive
	private int rentedCarId;
	
	@NotNull
	@Positive
	private int additionalServiceId;
}
