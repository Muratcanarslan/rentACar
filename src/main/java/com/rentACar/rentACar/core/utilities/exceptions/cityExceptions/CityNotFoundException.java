package com.rentACar.rentACar.core.utilities.exceptions.cityExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CityNotFoundException extends BusinessException {

	public CityNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
