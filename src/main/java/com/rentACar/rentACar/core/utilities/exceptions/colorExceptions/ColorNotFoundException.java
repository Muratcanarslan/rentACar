package com.rentACar.rentACar.core.utilities.exceptions.colorExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class ColorNotFoundException extends BusinessException{

	public ColorNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
