package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListDto;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.entities.concretes.OrderedAdditionalService;

public interface OrderedAdditionalServiceService {

	Result add(CreateOrderedAdditionalServiceRequest orderedAdditionalServiceRequest) throws BusinessException;

	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException;

	Result delete(int orderedAdditionalServiceId) throws BusinessException;
	
	DataResult<List<OrderedAdditionalServiceListDto>> getByRentedCarId(int rentedCarId) throws BusinessException;

	List<OrderedAdditionalService> getByRentedCarIdForBusiness(int rentedCarId) throws BusinessException;

	void deleteOrderedAdditionalServicesByRentedCarId(int rentedCarId) throws BusinessException;
}
