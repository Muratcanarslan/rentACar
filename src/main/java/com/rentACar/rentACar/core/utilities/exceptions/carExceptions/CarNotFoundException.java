package com.rentACar.rentACar.core.utilities.exceptions.carExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarNotFoundException extends BusinessException{

	public CarNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
