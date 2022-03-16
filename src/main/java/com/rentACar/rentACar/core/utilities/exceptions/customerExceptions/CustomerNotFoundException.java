package com.rentACar.rentACar.core.utilities.exceptions.customerExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CustomerNotFoundException extends BusinessException {

	public CustomerNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
