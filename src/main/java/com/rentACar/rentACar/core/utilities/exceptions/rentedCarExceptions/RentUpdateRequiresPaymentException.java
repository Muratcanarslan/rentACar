package com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class RentUpdateRequiresPaymentException extends BusinessException{

	public RentUpdateRequiresPaymentException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
