package com.rentACar.rentACar.core.utilities.exceptions.brandExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class BrandNotFoundException extends BusinessException{

	public BrandNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
