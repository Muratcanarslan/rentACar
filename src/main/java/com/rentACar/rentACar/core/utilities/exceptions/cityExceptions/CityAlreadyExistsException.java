package com.rentACar.rentACar.core.utilities.exceptions.cityExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CityAlreadyExistsException extends BusinessException{

	public CityAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
