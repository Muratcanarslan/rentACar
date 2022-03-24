package com.rentACar.rentACar.business.dtos.paymentDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentListDto {
	
	private int paymentId;

	private double totalPrice;

	private int Customer_CustomerId;

	private int RentedCar_RentedCarId;
	
	private int Invoice_InvoiceId;

}