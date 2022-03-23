package com.rentACar.rentACar.core.utilities.exceptions.colorExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class ColorAlreadyExistsException extends BusinessException{

	public ColorAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
