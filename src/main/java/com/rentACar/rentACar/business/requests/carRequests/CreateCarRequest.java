package com.rentACar.rentACar.business.requests.carRequests;

import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	@NotNull
	private double dailyPrice;
	
	@NotNull
	@Size(min = 2,max=255)
	private String modelYear;
	
	@NotNull
	@Size(min = 2,max=255)
	private String description;
	
	@NotNull
	@Min(0)
	private int brandId;
	
	@NotNull
	@Min(0)
	private int colorId;
}
