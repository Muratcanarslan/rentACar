package com.rentACar.rentACar.business.requests.rentedCarRequests;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentedCarRequestForCorporateCustomer {

	@NotNull
	@FutureOrPresent
	private LocalDate rentDate;

	@NotNull
	@FutureOrPresent
	private LocalDate confirmedPaidedDate;

	@NotNull
	@Min(0)
	private int hireCityId;

	@NotNull
	@Min(0)
	private int returnCityId;

	@NotNull
	@Min(0)
	private int customerId;

	@NotNull
	@Min(0)
	private int carId;
}
