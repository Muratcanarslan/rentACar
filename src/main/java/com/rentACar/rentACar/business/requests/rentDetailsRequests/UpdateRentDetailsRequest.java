package com.rentACar.rentACar.business.requests.rentDetailsRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentDetailsRequest {
	
	@NotNull
	@Positive
	private double differentCityDeliveryPrice;

}
