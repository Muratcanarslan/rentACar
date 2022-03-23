package com.rentACar.rentACar.core.utilities.exceptions.orderedAdditionalServiceExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class OrderedAdditionalServiceNotFoundException extends BusinessException{

	public OrderedAdditionalServiceNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
