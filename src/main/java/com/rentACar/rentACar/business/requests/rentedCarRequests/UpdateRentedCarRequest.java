package com.rentACar.rentACar.business.requests.rentedCarRequests;

import java.util.Date;

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
	private Date rentDate;

	private Date returnDate;
	
	@NotNull
	@Min(0)
	private int carId;
}
