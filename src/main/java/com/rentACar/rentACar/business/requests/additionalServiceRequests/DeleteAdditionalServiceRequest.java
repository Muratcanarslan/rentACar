package com.rentACar.rentACar.business.requests.additionalServiceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAdditionalServiceRequest {
	
	@NotNull
	@Positive
	private int additionalServiceId;
}
