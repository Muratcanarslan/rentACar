package com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class IndividualCustomerAlreadyExistsByNationalIdentityException extends BusinessException{

	public IndividualCustomerAlreadyExistsByNationalIdentityException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
