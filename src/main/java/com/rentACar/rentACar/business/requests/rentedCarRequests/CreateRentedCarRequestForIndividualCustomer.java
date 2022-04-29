package com.rentACar.rentACar.business.requests.rentedCarRequests;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentedCarRequestForIndividualCustomer {
	
	@NotNull
	@FutureOrPresent
	private LocalDate rentDate;
	
	@NotNull
	@FutureOrPresent
	private LocalDate confirmedPaidedDate;
	
	@NotNull
	@Positive
	private int customerId;
	
	@NotNull
	@Positive
	private int carId;
	
	@NotNull
	@Positive
	private int hireCityId;
	
	@NotNull
	@Positive
	private int returnCityId;
	
}
