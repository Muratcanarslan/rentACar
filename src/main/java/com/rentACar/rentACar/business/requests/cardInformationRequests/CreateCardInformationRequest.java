package com.rentACar.rentACar.business.requests.cardInformationRequests;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardInformationRequest {

	private String cardNumber;

	private String CVV;

	private String cardDate;

	private String ownerName;

	@JsonProperty(value = "customerId")
	private int userId;

}
