package com.rentACar.rentACar.business.requests;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	private double dailyPrice;
	
	private String modelYear;
	
	private String description;
	
	private int brandId;
	
	private int colorId;
}
