package com.rentACar.rentACar.core.utilities.exceptions.customerExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CustomerAlreadyExistsException extends BusinessException{

	public CustomerAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
