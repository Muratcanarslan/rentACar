package com.rentACar.rentACar.core.utilities.exceptions.carCrashExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarCrashInformationNotFoundException extends BusinessException {

	public CarCrashInformationNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
