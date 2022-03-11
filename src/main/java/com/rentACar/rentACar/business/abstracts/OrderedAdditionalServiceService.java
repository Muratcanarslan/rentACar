package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListDto;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

public interface OrderedAdditionalServiceService {
	
	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException;
	
	DataResult<List<OrderedAdditionalServiceListDto>> getByRentedCarId(int rentedCarId) throws BusinessException;
	
	void addOrderedAdditionalServicesByAdditionalIdListAndRentedCarId(List<Integer> additionalIds,int rentalId) throws BusinessException;
	
	void deleteOrderedAdditionalServicesByRentedCarId(int rentedCarId) throws BusinessException;
}
