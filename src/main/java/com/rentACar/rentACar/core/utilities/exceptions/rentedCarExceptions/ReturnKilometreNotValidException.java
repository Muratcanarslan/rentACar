package com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class ReturnKilometreNotValidException extends BusinessException{

	public ReturnKilometreNotValidException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
