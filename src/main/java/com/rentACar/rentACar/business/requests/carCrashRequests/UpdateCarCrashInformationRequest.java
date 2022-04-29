package com.rentACar.rentACar.business.requests.carCrashRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarCrashInformationRequest {

	@NotNull
	@Positive
	private int carCrashInformationId;

	@NotNull
	@Size(min = 2, max = 255)
	private String carCrashInformationDetails;

	@NotNull
	@Positive
	private int carId;
}
