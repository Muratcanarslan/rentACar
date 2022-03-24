package com.rentACar.rentACar.business.requests.creditCardInformationRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCreditCardInformationRequest {
	
	@NotNull
	@Positive
	private int creditCardInformationId;

}
