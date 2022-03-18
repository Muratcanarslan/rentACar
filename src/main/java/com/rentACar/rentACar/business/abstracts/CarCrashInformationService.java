package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListByCarDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.GetCarCrashInformationDto;
import com.rentACar.rentACar.business.requests.carCrashRequests.CreateCarCrashInformationRequest;
import com.rentACar.rentACar.business.requests.carCrashRequests.UpdateCarCrashInformationRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.exceptions.carCrashExceptions.CarCrashInformationNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CarCrashInformationService {
	
	Result add(CreateCarCrashInformationRequest createCarCrashInformationRequest) throws BusinessException;

	Result update(UpdateCarCrashInformationRequest updateCarCrashInformationRequest) throws CarCrashInformationNotFoundException, BusinessException;
	
	Result delete(int carCrashInformationId) throws CarCrashInformationNotFoundException;
	
	DataResult<GetCarCrashInformationDto> getById(int carCrashInformationId) throws CarCrashInformationNotFoundException;
	
	DataResult<List<CarCrashInformationListByCarDto>> getByCarId(int carId) throws BusinessException;
	
	DataResult<List<CarCrashInformationListDto>> getAll();
	
}
