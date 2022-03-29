package com.rentACar.rentACar.business.requests.rentedCarRequests;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRentedCarRequest {

	@NotNull
	@Positive
	private int rentedCarId;

}
