package com.rentACar.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListLessThanDto {
    
	
	private int carId;
	
	private double dailyPrice;
	
	private String modelYear;
	
	private String description;
	
	private int brandId;
	
	private int colorId;
}
