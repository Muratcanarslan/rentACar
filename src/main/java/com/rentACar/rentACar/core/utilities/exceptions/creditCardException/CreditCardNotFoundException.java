package com.rentACar.rentACar.core.utilities.exceptions.creditCardException;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CreditCardNotFoundException extends BusinessException{

	public CreditCardNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
