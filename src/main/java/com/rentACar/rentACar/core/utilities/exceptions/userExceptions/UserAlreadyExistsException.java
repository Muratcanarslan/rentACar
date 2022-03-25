package com.rentACar.rentACar.core.utilities.exceptions.userExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends BusinessException {

	public UserAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
