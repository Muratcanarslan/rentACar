package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListByCarDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.CarCrashInformationListDto;
import com.rentACar.rentACar.business.dtos.carCrashInfomationDtos.GetCarCrashInformationDto;
import com.rentACar.rentACar.business.requests.carCrashRequests.CreateCarCrashInformationRequest;
import com.rentACar.rentACar.business.requests.carCrashRequests.DeleteCarCrashInformationRequest;
import com.rentACar.rentACar.business.requests.carCrashRequests.UpdateCarCrashInformationRequest;
import com.rentACar.rentACar.core.utilities.exceptions.carCrashExceptions.CarCrashInformationNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CarCrashInformationService {

	Result add(CreateCarCrashInformationRequest createCarCrashInformationRequest) throws CarNotFoundException;

	Result update(UpdateCarCrashInformationRequest updateCarCrashInformationRequest) throws CarNotFoundException, CarCrashInformationNotFoundException;

	Result delete(DeleteCarCrashInformationRequest deleteCarCrashInformationRequest) throws CarCrashInformationNotFoundException;

	DataResult<GetCarCrashInformationDto> getById(int carCrashInformationId) throws CarCrashInformationNotFoundException;

	DataResult<List<CarCrashInformationListByCarDto>> getByCarId(int carId) throws CarNotFoundException;

	DataResult<List<CarCrashInformationListDto>> getAll(int pageNo,int pageSize);

}
