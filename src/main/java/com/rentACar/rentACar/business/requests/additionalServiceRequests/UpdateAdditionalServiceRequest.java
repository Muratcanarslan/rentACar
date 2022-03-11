package com.rentACar.rentACar.business.requests.additionalServiceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
	
	private int additionalServiceId;
	
	private String additionalServiceName;
	
	private double additionalServiceDailyPrice;	
}