package com.rentACar.rentACar.business.dtos.carDtos;

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

	private double kilometreInformation;

	private int brandId;

	private int colorId;
}
