package com.rentACar.rentACar.business.abstracts;

import java.util.List;

import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListDto;
import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListForRentedCarDto;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.orderedAdditionalServiceExceptions.OrderedAdditionalServiceAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.orderedAdditionalServiceExceptions.OrderedAdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.entities.concretes.OrderedAdditionalService;

public interface OrderedAdditionalServiceService {

	Result add(CreateOrderedAdditionalServiceRequest orderedAdditionalServiceRequest)
			throws OrderedAdditionalServiceAlreadyExistsException, AdditionalServiceNotFoundException,
			RentedCarNotFoundException;

	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest)
			throws OrderedAdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException;

	Result delete(int orderedAdditionalServiceId) throws OrderedAdditionalServiceNotFoundException;

	DataResult<List<OrderedAdditionalServiceListForRentedCarDto>> getByRentedCarId(int rentedCarId)
			throws RentedCarNotFoundException;

	DataResult<List<OrderedAdditionalServiceListDto>> getAll(int pageNo, int pageSize);

	List<OrderedAdditionalService> getByRentedCarIdForBusiness(int rentedCarId) throws RentedCarNotFoundException;

	void deleteOrderedAdditionalServicesByRentedCarId(int rentedCarId) throws RentedCarNotFoundException;

	void addOrderedAdditionalServiceForPayment(List<Integer> additionalServiceIds, int rentedCarId)
			throws AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException;
}
