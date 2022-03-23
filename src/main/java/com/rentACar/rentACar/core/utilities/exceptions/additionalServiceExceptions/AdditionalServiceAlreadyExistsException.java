package com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class AdditionalServiceAlreadyExistsException extends BusinessException{

	public AdditionalServiceAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
