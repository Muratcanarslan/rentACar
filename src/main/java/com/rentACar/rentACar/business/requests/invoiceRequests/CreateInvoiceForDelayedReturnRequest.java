package com.rentACar.rentACar.business.requests.invoiceRequests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceForDelayedReturnRequest {
	
	@NotNull
	@Min(0)
	private int RentedCar_RentedCarId;
	

}
