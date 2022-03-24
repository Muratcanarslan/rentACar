package com.rentACar.rentACar.core.utilities.exceptions.paymentExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class PaymentNotFoundException extends BusinessException{

	public PaymentNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
