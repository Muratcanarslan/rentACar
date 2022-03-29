package com.rentACar.rentACar.business.requests.carCrashRequests;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarCrashInformationRequest {
	
	@NotNull
	@Positive
	private int carCrashInformationId;

}
