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
public class UpdateRentedCarRequest {

	@NotNull
	@Min(0)
	private int rentedCarId;

	@NotNull
	@FutureOrPresent
	private LocalDate returnDate;

	@NotNull
	@Min(0)
	private double returnKilometre;
	
	
}
