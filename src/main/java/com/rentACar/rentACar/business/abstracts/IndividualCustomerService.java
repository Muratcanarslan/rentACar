package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.individualCustomerDtos.GetIndividualCustomerDto;
import com.rentACar.rentACar.business.dtos.individualCustomerDtos.IndividualCustomerListDto;
import com.rentACar.rentACar.business.requests.individualCustomerDtos.CreateIndividualCustomerRequest;
import com.rentACar.rentACar.business.requests.individualCustomerDtos.UpdateIndividualCustomerRequest;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerAlreadyExistsByNationalIdentityException;
import com.rentACar.rentACar.core.utilities.exceptions.indiviualCustomerExceptions.IndividualCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.userExceptions.UserAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface IndividualCustomerService {

	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws UserAlreadyExistsException, IndividualCustomerAlreadyExistsByNationalIdentityException;

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws IndividualCustomerNotFoundException, UserAlreadyExistsException, IndividualCustomerAlreadyExistsByNationalIdentityException;
	
	Result delete(int individualCustomerId) throws IndividualCustomerNotFoundException;
	
	DataResult<GetIndividualCustomerDto> getById(int individualCustomerId) throws IndividualCustomerNotFoundException;
	
	DataResult<List<IndividualCustomerListDto>> getAll();
}