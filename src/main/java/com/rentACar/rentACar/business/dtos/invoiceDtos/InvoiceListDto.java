package com.rentACar.rentACar.business.dtos.invoiceDtos;

import java.sql.Date;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {
	
	private int invoiceId;

	private Date creationDate;

	private LocalDate rentDate;

	private LocalDate returnDate;

	private LocalDate confirmedPaidedDate;

	private int totalRentDays;

	private double totalPrice;
	
	@JsonProperty(value = "rentedCarId")
	private int RentedCar_RentedCarId;

	@JsonProperty(value = "customerId")
	private int Customer_CustomerId;
}
