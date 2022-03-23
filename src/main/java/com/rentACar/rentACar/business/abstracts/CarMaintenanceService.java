package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.GetCarMaintenanceDto;
import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.carExceptions.CarNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarAlreadyInMaintenanceException;
import com.rentACar.rentACar.core.utilities.exceptions.carMaintenanceExceptions.CarMaintenanceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.CarAlreadyInRentException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CarMaintenanceService {

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws CarNotFoundException, CarAlreadyInMaintenanceException, CarAlreadyInRentException;

	Result delete(int carMaintenanceId) throws CarMaintenanceNotFoundException;

	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws CarMaintenanceNotFoundException, CarNotFoundException;

	DataResult<GetCarMaintenanceDto> getById(int carMaintenanceId) throws CarMaintenanceNotFoundException;

	DataResult<List<CarMaintenanceListDto>> getAll();

	void checkIfCarMaintenanceIsExistsByCarId(int carId) throws CarAlreadyInMaintenanceException;

}
