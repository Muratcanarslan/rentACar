package com.rentACar.rentACar.business.requests.cityRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCityRequest {
	
	@Positive
	@NotNull
	private int cityId;
	

}
