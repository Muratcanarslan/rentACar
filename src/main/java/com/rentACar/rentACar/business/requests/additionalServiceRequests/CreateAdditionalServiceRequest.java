package com.rentACar.rentACar.business.requests.additionalServiceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {
	
	@NotNull
	@Size(min = 1,max = 255)
	private String additionalServiceName;
	
	@NotNull
	@Positive
	private double additionalServiceDailyPrice;
	

}
