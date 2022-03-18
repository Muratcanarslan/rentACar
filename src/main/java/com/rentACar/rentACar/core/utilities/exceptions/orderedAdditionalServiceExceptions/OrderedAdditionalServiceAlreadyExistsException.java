package com.rentACar.rentACar.core.utilities.exceptions.orderedAdditionalServiceExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class OrderedAdditionalServiceAlreadyExistsException extends BusinessException {

	public OrderedAdditionalServiceAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
