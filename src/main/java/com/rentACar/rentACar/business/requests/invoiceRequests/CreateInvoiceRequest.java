package com.rentACar.rentACar.business.requests.invoiceRequests;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

	@NotNull
	@Min(0)
	private int totalRentDays;
	
	@NotNull
	@Min(0)
	private double totalPrice;
	
	@NotNull
	@Min(0)
	private int RentedCar_RentedCarId;
	
}
