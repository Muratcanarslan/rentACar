package com.rentACar.rentACar.business.requests.creditCardInformationRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardInformationRequest {

	@NotNull
	@Pattern(regexp = "^[0-9]{16}")
	private String cardNumber;

	@NotNull
	@Pattern(regexp = "^[0-9]{3}")
	private String CVV;

	@NotNull
	private String cardExpireDate;

	@NotNull
	@Pattern(regexp = "^[a-zA-Z]{2,255}")
	private String ownerName;

	@NotNull
	@Positive
	@JsonProperty(value = "customerId")
	private int Customer_CustomerId;

}
