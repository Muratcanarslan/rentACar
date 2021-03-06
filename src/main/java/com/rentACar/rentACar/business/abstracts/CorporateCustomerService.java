package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.rentACar.rentACar.business.dtos.corporateCustomerDtos.GetCorporateCustomerDto;
import com.rentACar.rentACar.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.rentACar.rentACar.business.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.rentACar.rentACar.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerAlreadyExistsByTaxNumberException;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.userExceptions.UserAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CorporateCustomerService {
	
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws UserAlreadyExistsException, CorporateCustomerAlreadyExistsByTaxNumberException;
	
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws UserAlreadyExistsException, CorporateCustomerAlreadyExistsByTaxNumberException, CorporateCustomerNotFoundException;
	
	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws CorporateCustomerNotFoundException;
	
	DataResult<GetCorporateCustomerDto> getById(int corporateCustomerId) throws CorporateCustomerNotFoundException;
	
	DataResult<List<CorporateCustomerListDto>> getAll(int pageNo,int pageSize);
	
	void checkIfCorporateCustomerExists(int corporateCustomerId) throws CorporateCustomerNotFoundException;


}
