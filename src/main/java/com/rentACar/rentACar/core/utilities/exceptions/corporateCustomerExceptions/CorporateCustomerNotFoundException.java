package com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CorporateCustomerNotFoundException extends BusinessException{

	public CorporateCustomerNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
