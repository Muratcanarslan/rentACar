package com.rentACar.rentACar.core.utilities.exceptions.brandExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class BrandAlreadyExistsException extends BusinessException {

	public BrandAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
