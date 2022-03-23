package com.rentACar.rentACar.core.utilities.exceptions.bankServiceExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class PaymentNotSuccessfullException extends BusinessException{

	public PaymentNotSuccessfullException(String exceptionMessage) {
		super(exceptionMessage);
	} 

}
