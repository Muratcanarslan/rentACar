package com.rentACar.rentACar.business.dtos.rentedCarDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentedCarListDto {

	private int rentedCarId;

	private LocalDate rentDate;

	private LocalDate returnDate;

	private LocalDate confirmedPaidedDate;

	private int carId;

	private double totalPrice;

	private int hireCityId;

	private int returnCityId;
}
