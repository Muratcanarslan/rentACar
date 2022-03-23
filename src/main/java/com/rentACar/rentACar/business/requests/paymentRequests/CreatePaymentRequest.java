package com.rentACar.rentACar.business.requests.paymentRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	@Min(0)
	private int Customer_CustomerId;
	
	@NotNull
	@Min(0)
	private int RentedCar_RentedCarId;
	
	@NotNull
	@Min(0)
	private int Invoice_InvoiceId;
}
