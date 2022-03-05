package com.rentACar.rentACar.business.dtos.rentedCarDtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentedCarDto {

	private int rentedCarId;

	private Date rentDate;

	private Date returnDate;

	private int carId;
}
