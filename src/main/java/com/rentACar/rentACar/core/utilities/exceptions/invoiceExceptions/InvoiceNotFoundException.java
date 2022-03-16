package com.rentACar.rentACar.core.utilities.exceptions.invoiceExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class InvoiceNotFoundException extends BusinessException{

	public InvoiceNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
