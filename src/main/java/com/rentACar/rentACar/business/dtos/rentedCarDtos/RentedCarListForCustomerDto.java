package com.rentACar.rentACar.business.dtos.rentedCarDtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentedCarListForCustomerDto {
	
	private int rentedCarId;

	private LocalDate rentDate;

	private LocalDate returnDate;

	private LocalDate confirmedPaidedDate;

	private int carId;

	private double rentKilometre;

	private double returnKilometre;

	private int hireCityId;

	private int returnCityId;

	@JsonProperty(value = "customerId")
	private int Customer_CustomerId;
}
