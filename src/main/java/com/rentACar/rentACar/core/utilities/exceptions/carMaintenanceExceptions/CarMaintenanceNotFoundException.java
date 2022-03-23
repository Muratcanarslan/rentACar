package com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarMaintenanceNotFoundException extends BusinessException{

	public CarMaintenanceNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
