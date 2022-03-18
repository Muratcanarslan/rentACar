package com.rentACar.rentACar.core.utilities.exceptions.userExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends BusinessException {

	private String data;

	public UserAlreadyExistsException(String exceptionMessage, String data) {
		super(exceptionMessage);
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
