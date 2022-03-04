package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.CarMaintenanceDto;
import com.rentACar.rentACar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.rentACar.rentACar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface CarMaintenanceService {
	 
	
	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
	
	Result delete(int carMaintenanceId) throws BusinessException;
	
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;
	
	DataResult<CarMaintenanceDto> getById(int carMaintenanceId) throws BusinessException;
	
	DataResult<List<CarMaintenanceListDto>> getAll() throws BusinessException;
	
}
