package com.rentACar.rentACar.business.requests.rentedCarRequests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentedCarForDelayedReturnRequest {

	@NotNull
	@Positive
	private int rentedCarId;

	@NotNull
	private LocalDate returnDate;

	@NotNull
	@Positive
	private double returnKilometre;


}
