package com.rentACar.rentACar.business.dtos.carDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListDto {

	private int carId;
	
	private double kilometreInformation;
	
	private String description;
	
	private String modelYear;

	private String brandName;

	private String colorName;

}
