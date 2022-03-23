package com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class AdditionalServiceNotFoundException extends BusinessException {

	public AdditionalServiceNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
