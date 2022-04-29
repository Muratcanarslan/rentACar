package com.rentACar.rentACar.business.dtos.paymentDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentListByCustomerDto {

	private int paymentId;

	private double totalPrice;
	
	@JsonProperty(value = "customerId")
	private int Customer_CustomerId;

	@JsonProperty(value = "rentedCarId")
	private int RentedCar_RentedCarId;
}
