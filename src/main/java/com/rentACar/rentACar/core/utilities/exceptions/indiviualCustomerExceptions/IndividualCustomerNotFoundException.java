package com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class IndividualCustomerNotFoundException extends BusinessException{

	public IndividualCustomerNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
