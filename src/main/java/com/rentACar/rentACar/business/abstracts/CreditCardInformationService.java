package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.creditCardInformationDtos.CardInformationListDto;
import com.rentACar.rentACar.business.dtos.creditCardInformationDtos.CreditCardInformationByCustomerDto;
import com.rentACar.rentACar.business.requests.creditCardInformationRequests.CreateCreditCardInformationRequest;
import com.rentACar.rentACar.business.requests.creditCardInformationRequests.DeleteCreditCardInformationRequest;
import com.rentACar.rentACar.core.utilities.exceptions.creditCardException.CreditCardAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.creditCardException.CreditCardNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.customerExceptions.CustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CreditCardInformationService {

	Result add(CreateCreditCardInformationRequest createCardInformationRequest) throws CustomerNotFoundException, CreditCardAlreadyExistsException;

	Result delete(DeleteCreditCardInformationRequest deleteCreditCardInformationRequest)
			throws CreditCardNotFoundException;

	DataResult<List<CreditCardInformationByCustomerDto>> getByCustomerId(int customerId)
			throws CustomerNotFoundException;

	DataResult<List<CardInformationListDto>> getAll(int pageNo,int pageSize);

}
