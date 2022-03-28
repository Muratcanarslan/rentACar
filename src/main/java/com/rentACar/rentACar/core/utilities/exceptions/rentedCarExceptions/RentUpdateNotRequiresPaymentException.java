package com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class RentUpdateNotRequiresPaymentException extends BusinessException{

	public RentUpdateNotRequiresPaymentException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
