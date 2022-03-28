package com.rentACar.rentACar.business.dtos.invoiceDtos;

import java.sql.Date;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRentedCarListDto {
	
	private int invoiceId;

	private Date creationDate;

	private LocalDate rentDate;

	private LocalDate returnDate;

	private LocalDate confirmedPaidedDate;

	private int totalRentDays;

	private double totalPrice;
}
