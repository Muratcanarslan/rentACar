package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.rentedCarDtos.GetRentedCarDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListDto;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForCorporateCustomer;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequestForIndividualCustomer;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface RentedCarService {
	
	
	Result addForIndividualCustomer(CreateRentedCarRequestForIndividualCustomer createRentedCarRequest) throws BusinessException;
	
	Result addForCorporateCustomer(CreateRentedCarRequestForCorporateCustomer  createRentedCarRequestForCorporateCustomer) throws CorporateCustomerNotFoundException, BusinessException;
	
	Result update(UpdateRentedCarRequest updateRentedCarRequest) throws BusinessException;
	
	Result delete(int rentedCarId) throws BusinessException;
	
	DataResult<GetRentedCarDto> getById(int rentedCarId) throws BusinessException;
	
	DataResult<List<RentedCarListDto>> getAll();
	
	void checkIfCarIsAlreadyRentedByCarId(int carId) throws BusinessException;
	
	void checkIfRentedCarIsExistsByRentedCarId(int rentedCarId) throws BusinessException;
}
