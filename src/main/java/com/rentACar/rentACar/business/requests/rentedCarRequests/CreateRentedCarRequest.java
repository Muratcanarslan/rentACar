package com.rentACar.rentACar.business.requests.rentedCarRequests;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentedCarRequest {
	
	@NotNull
	private LocalDate rentDate;

	private LocalDate returnDate;
	
	@NotNull
	private LocalDate confirmedPaidedDate;

	
	@NotNull
	@Min(0)
	private int carId;
	
	@NotNull
	@Min(0)
	private int hireCityId;
	
	@NotNull
	@Min(0)
	private int returnCityId;
	
	@NotNull
	private List<Integer> additionalServiceIds;

}