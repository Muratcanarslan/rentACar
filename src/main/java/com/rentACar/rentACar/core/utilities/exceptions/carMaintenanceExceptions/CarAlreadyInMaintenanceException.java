package com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CarAlreadyInMaintenanceException extends BusinessException{

	public CarAlreadyInMaintenanceException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
