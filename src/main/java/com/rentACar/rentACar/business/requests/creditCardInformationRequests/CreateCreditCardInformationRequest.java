package com.rentACar.rentACar.business.requests.creditCardInformationRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardInformationRequest {

	@NotNull
	@Size(min = 16,max=16)
	private String cardNumber;

	@NotNull
	@Size(min=3,max=3)
	private String CVV;

	@NotNull
	private String cardExpireDate;

	@NotNull
	private String ownerName;

	@NotNull
	@Positive
	@JsonProperty(value = "customerId")
	private int Customer_CustomerId;

}
