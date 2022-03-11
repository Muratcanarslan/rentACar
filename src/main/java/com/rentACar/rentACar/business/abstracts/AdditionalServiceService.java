package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.rentACar.rentACar.business.dtos.additionalServiceDtos.GetAdditionalServiceDto;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface AdditionalServiceService {
	
	Result add(CreateAdditionalServiceRequest additionalServiceRequest) throws BusinessException;
	Result delete(int additionalServiceId) throws BusinessException;
	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;
	DataResult<GetAdditionalServiceDto> getById(int additionalServiceId) throws BusinessException;
	DataResult<List<AdditionalServiceListDto>> getAll();
	void checkIfAdditionalServiceExists(int id) throws BusinessException;
	void checkIfAdditionalServicesExists(List<Integer> additionalServiceIds) throws BusinessException;
	double calculateAdditionalServicePriceByAdditionalServiceIdListAndRentDateValue(List<Integer> additionalServiceIds,int rentDateValue) throws BusinessException;
}

