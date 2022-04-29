package com.rentACar.rentACar.core.utilities.exceptions.creditCardException;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CreditCardAlreadyExistsException extends BusinessException {

	public CreditCardAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
