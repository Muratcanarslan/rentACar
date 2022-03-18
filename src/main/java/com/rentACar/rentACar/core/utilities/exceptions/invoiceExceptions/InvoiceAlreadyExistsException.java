package com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class InvoiceAlreadyExistsException extends BusinessException{

	public InvoiceAlreadyExistsException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
