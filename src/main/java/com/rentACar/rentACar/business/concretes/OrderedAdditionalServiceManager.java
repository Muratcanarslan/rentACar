package com.rentACar.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.AdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.constants.messages.BusinessMessages;
import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListDto;
import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListForRentedCarDto;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.additionalServiceExceptions.AdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.orderedAdditionalServiceExceptions.OrderedAdditionalServiceAlreadyExistsException;
import com.rentACar.rentACar.core.utilities.exceptions.orderedAdditionalServiceExceptions.OrderedAdditionalServiceNotFoundException;
import com.rentACar.rentACar.core.utilities.exceptions.rentedCarExceptions.RentedCarNotFoundException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.rentACar.rentACar.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private ModelMapperService modelMapperService;
	private AdditionalServiceService additionalServiceService;
	private RentedCarService rentedCarService;

	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
			ModelMapperService modelMapperService, AdditionalServiceService additionalServiceService,
			@Lazy RentedCarService rentedCarService) {
		super();
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.additionalServiceService = additionalServiceService;
		this.rentedCarService = rentedCarService;
	}

	@Override
	public Result add(CreateOrderedAdditionalServiceRequest orderedAdditionalServiceRequest)
			throws OrderedAdditionalServiceAlreadyExistsException, AdditionalServiceNotFoundException,
			RentedCarNotFoundException {

		this.additionalServiceService
				.checkIfAdditionalServiceExists(orderedAdditionalServiceRequest.getAdditionalServiceId());
		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(orderedAdditionalServiceRequest.getRentedCarId());
		checkIfOrderedAdditionalServiceAlreadyExistsForRentedCar(
				orderedAdditionalServiceRequest.getAdditionalServiceId(),
				orderedAdditionalServiceRequest.getRentedCarId());

		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(orderedAdditionalServiceRequest, OrderedAdditionalService.class);

		this.orderedAdditionalServiceDao.save(orderedAdditionalService);

		return new SuccessResult(BusinessMessages.ADD_SUCCESSFULL);
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest)
			throws OrderedAdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException {

		checkIfOrderedAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		checkIfOrderedAdditionalServiceAlreadyExistsForRentedCar(
				updateOrderedAdditionalServiceRequest.getAdditionalServiceId(),
				updateOrderedAdditionalServiceRequest.getRentedCarId());

		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);

		this.orderedAdditionalServiceDao.save(orderedAdditionalService);

		return new SuccessResult(BusinessMessages.UPDATE_SUCCESSFULL);
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListForRentedCarDto>> getByRentedCarId(int rentedCarId)
			throws RentedCarNotFoundException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		List<OrderedAdditionalService> orderedAdditionalServices = this.orderedAdditionalServiceDao
				.getByRentedCar_RentedCarId(rentedCarId);

		List<OrderedAdditionalServiceListForRentedCarDto> orderedAdditionalServiceListDtos = orderedAdditionalServices
				.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService,
						OrderedAdditionalServiceListForRentedCarDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalServiceListForRentedCarDto>>(
				orderedAdditionalServiceListDtos, BusinessMessages.GET_SUCCESSFUL);
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<OrderedAdditionalService> orderedAdditionalServices = this.orderedAdditionalServiceDao.findAll(pageable)
				.getContent();

		List<OrderedAdditionalServiceListDto> orderedAdditionalServiceListDtos = orderedAdditionalServices.stream()
				.map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService,
						OrderedAdditionalServiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(orderedAdditionalServiceListDtos,
				BusinessMessages.GET_SUCCESSFUL);

	}

	@Override
	public List<OrderedAdditionalService> getByRentedCarIdForBusiness(int rentedCarId)
			throws RentedCarNotFoundException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		List<OrderedAdditionalService> additionalServices = this.orderedAdditionalServiceDao
				.getByRentedCar_RentedCarId(rentedCarId);

		return additionalServices;
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest)
			throws OrderedAdditionalServiceNotFoundException {

		checkIfOrderedAdditionalServiceExists(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());

		this.orderedAdditionalServiceDao
				.deleteById(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());

		return new SuccessResult(BusinessMessages.DELETE_SUCCESSFUL);
	}

	public void addOrderedAdditionalServiceForPayment(List<Integer> additionalServiceIds, int rentedCarId)
			throws AdditionalServiceNotFoundException, OrderedAdditionalServiceAlreadyExistsException,
			RentedCarNotFoundException {

		this.additionalServiceService.checkIfAdditionalServicesExists(additionalServiceIds);

		for (int id : additionalServiceIds) {

			CreateOrderedAdditionalServiceRequest additionalServiceRequest = new CreateOrderedAdditionalServiceRequest();

			additionalServiceRequest.setAdditionalServiceId(id);

			additionalServiceRequest.setRentedCarId(rentedCarId);

			this.add(additionalServiceRequest);
		}

	}

	public void deleteOrderedAdditionalServicesByRentedCarId(int rentedCarId) throws RentedCarNotFoundException {

		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);

		this.orderedAdditionalServiceDao.deleteAllByRentedCar_RentedCarId(rentedCarId);
	}

	public void checkIfOrderedAdditionalServiceExists(int orderedAdditionalServiceId)
			throws OrderedAdditionalServiceNotFoundException {

		if (!this.orderedAdditionalServiceDao.existsById(orderedAdditionalServiceId)) {
			throw new OrderedAdditionalServiceNotFoundException(
					BusinessMessages.ORDERED_ADDITIONAL_SERVICE_NOT_FOUND + orderedAdditionalServiceId);
		}
	}

	private void checkIfOrderedAdditionalServiceAlreadyExistsForRentedCar(int additionalServiceId, int rentedCarId)
			throws OrderedAdditionalServiceAlreadyExistsException {
		if (this.orderedAdditionalServiceDao.existsByAdditionalService_AdditionalServiceIdAndRentedCar_RentedCarId(
				additionalServiceId, rentedCarId)) {
			throw new OrderedAdditionalServiceAlreadyExistsException(
					BusinessMessages.ORDERED_ADDITIONAL_SERVICE_ALREADY_EXISTS);
		}

	}

}
