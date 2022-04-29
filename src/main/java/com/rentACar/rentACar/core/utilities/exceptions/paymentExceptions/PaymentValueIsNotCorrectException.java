package com.rentACar.rentACar.core.utilities.exceptions.paymentExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class PaymentValueIsNotCorrectException extends BusinessException {

	public PaymentValueIsNotCorrectException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
