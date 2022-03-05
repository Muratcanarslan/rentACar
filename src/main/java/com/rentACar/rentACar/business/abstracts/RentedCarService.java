package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.rentedCarDtos.GetRentedCarDto;
import com.rentACar.rentACar.business.dtos.rentedCarDtos.RentedCarListDto;
import com.rentACar.rentACar.business.requests.rentedCarRequests.CreateRentedCarRequest;
import com.rentACar.rentACar.business.requests.rentedCarRequests.UpdateRentedCarRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface RentedCarService {
	
	
	Result add(CreateRentedCarRequest createRentedCarRequest) throws BusinessException;
	
	Result update(UpdateRentedCarRequest updateRentedCarRequest) throws BusinessException;
	
	Result delete(int rentedCarId) throws BusinessException;
	
	DataResult<GetRentedCarDto> getById(int rentedCarId) throws BusinessException;
	
	DataResult<List<RentedCarListDto>> getAll() throws BusinessException;
	
	void checkIfCarIsAlreadyRentedByCarId(int carId) throws BusinessException;
}
