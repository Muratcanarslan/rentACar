package com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarAlreadyInRentException extends BusinessException{

	public CarAlreadyInRentException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
