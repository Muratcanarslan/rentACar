package com.rentACar.rentACar.business.requests.bankServiceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBankServiceRequest {

	@NotNull
	@Pattern(regexp = "^[0-9]{16}")
	private String cardNumber;

	@NotNull
	@Pattern(regexp = "^[0-9]{3}")
	private String cvv;

	@NotNull
	@Pattern(regexp = "^[a-zA-Z]{2,255}")
	private String name;

	@NotNull
	@Positive
	private double totalPrice;
}
