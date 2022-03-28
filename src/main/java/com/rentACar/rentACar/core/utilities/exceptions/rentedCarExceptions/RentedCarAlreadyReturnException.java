package com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class RentedCarAlreadyReturnException extends BusinessException{

	public RentedCarAlreadyReturnException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
