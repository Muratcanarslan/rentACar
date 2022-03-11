package com.rentACar.rentACar.business.requests.cityRequest;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {

	@NotNull
	@Min(0)
	private int cityId;
	
	@NotNull
	@Size(min = 2,max=255)
	private String cityName;
}
