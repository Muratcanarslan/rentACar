package com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions;

import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;

@SuppressWarnings("serial")
public class CorporateCustomerAlreadyExistsByTaxNumberException extends BusinessException {

	public CorporateCustomerAlreadyExistsByTaxNumberException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
