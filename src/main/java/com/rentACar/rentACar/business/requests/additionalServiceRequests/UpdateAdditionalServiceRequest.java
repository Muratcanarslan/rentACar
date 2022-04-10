package com.rentACar.rentACar.business.requests.additionalServiceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
	
	@Positive
	@NotNull
	private int additionalServiceId;
	
	@NotNull
	private String additionalServiceName;
	
	@NotNull
	@Positive
	private double additionalServiceDailyPrice;	
}
