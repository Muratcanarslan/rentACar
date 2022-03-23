package com.rentACar.rentACar.core.utilities.exceptions.paymentExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class PaymentAlreadyExistsException extends BusinessException{

	public PaymentAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
