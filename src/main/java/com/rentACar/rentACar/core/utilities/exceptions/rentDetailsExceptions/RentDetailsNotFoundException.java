package com.rentACar.rentACar.core.utilities.exceptions.rentDetailsExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class RentDetailsNotFoundException extends BusinessException{

	public RentDetailsNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
