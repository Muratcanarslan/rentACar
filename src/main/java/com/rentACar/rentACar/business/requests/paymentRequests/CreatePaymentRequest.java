package com.rentACar.rentACar.business.requests.paymentRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	
	@NotNull
	@Min(0)
	private double totalPrice; 
	
	@NotNull
	@Positive
	@JsonProperty(value = "customerId")
	private int Customer_CustomerId;
	
	@NotNull
	@Positive
	@JsonProperty(value = "rentedCarId")
	private int RentedCar_RentedCarId;
	
	@NotNull
	@Positive
	@JsonProperty(value = "invoiceId")
	private int Invoice_InvoiceId;
}
