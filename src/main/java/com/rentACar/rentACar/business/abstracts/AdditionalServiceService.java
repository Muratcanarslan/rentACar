package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.rentACar.rentACar.business.dtos.additionalServiceDtos.GetAdditionalServiceDto;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface AdditionalServiceService {

	Result add(CreateAdditionalServiceRequest additionalServiceRequest) throws AdditionalServiceAlreadyExistsException;

	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws AdditionalServiceNotFoundException;

	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest)
			throws AdditionalServiceNotFoundException;

	DataResult<GetAdditionalServiceDto> getById(int additionalServiceId) throws AdditionalServiceNotFoundException;

	DataResult<List<AdditionalServiceListDto>> getAll(int pageNo, int pageSize);

	void checkIfAdditionalServiceExists(int id) throws AdditionalServiceNotFoundException;

	void checkIfAdditionalServicesExists(List<Integer> additionalServiceIds) throws AdditionalServiceNotFoundException;

	double calculateAdditionalServicePriceByAdditionalServiceIdListAndRentDateValue(List<Integer> additionalServiceIds,
			int rentDateValue) throws AdditionalServiceNotFoundException;
}
