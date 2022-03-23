package com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class RentedCarNotFoundException extends BusinessException {

	public RentedCarNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
