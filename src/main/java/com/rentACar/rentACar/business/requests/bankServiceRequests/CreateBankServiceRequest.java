package com.rentACar.rentACar.business.requests.bankServiceRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBankServiceRequest {

	private String cardNumber;

	private String cvv;

	private String name;

	private double totalPrice;
}
