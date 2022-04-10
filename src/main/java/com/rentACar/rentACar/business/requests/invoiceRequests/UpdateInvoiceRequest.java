package com.rentACar.rentACar.business.requests.invoiceRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {

	@Positive
	@NotNull
	@JsonProperty(value = "rentedCarId")
	private int RentedCar_RentedCarId;
	
}
