package com.rentACar.rentACar.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rentACar.rentACar.business.abstracts.AdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.abstracts.RentedCarService;
import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListDto;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.mapping.ModelMapperService;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;
import com.rentACar.rentACar.core.utilities.results.SuccessDataResult;
import com.rentACar.rentACar.core.utilities.results.SuccessResult;
import com.rentACar.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.rentACar.rentACar.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService{
	
	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private ModelMapperService modelMapperService;
	private AdditionalServiceService additionalServiceService;
	private RentedCarService rentedCarService;

	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
			ModelMapperService modelMapperService,AdditionalServiceService additionalServiceService,
			@Lazy RentedCarService rentedCarService
			) {
		super();
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.additionalServiceService = additionalServiceService;
		this.rentedCarService = rentedCarService;
	}

	

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
		checkIfOrderedAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessResult("updated");
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getByRentedCarId(int rentedCarId) throws BusinessException {
		
		this.rentedCarService.checkIfRentedCarIsExistsByRentedCarId(rentedCarId);
		
		List<OrderedAdditionalService> orderedAdditionalServices = this.orderedAdditionalServiceDao.getByRentedCar_RentedCarId(rentedCarId);
		
		List<OrderedAdditionalServiceListDto> orderedAdditionalServiceListDtos = 
				orderedAdditionalServices.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(orderedAdditionalServiceListDtos, "get All");
	}

	@Override
	public void addOrderedAdditionalServicesByAdditionalIdListAndRentedCarId(List<Integer> additionalIds,int rentedCarId) throws BusinessException {
        
		this.additionalServiceService.checkIfAdditionalServicesExists(additionalIds);
		
		List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequests = createAdditionalServiceRequestByAdditionalListIdsAndRentedCarId(additionalIds,rentedCarId);
		
		List<OrderedAdditionalService> additionalServices = createOrderedAdditionalServiceRequests.stream().map(k -> this.modelMapperService.forRequest().map(k,OrderedAdditionalService.class)).collect(Collectors.toList());
		
		this.orderedAdditionalServiceDao.saveAll(additionalServices);
		
	}
	
	private List<CreateOrderedAdditionalServiceRequest> createAdditionalServiceRequestByAdditionalListIdsAndRentedCarId(List<Integer> additionalIds,int rentedCarId){
      
		List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequests = new ArrayList<CreateOrderedAdditionalServiceRequest>();
		
		
		for(int additionalId : additionalIds) {
			createOrderedAdditionalServiceRequests.add(new CreateOrderedAdditionalServiceRequest(rentedCarId,additionalId));
		}
		
		return createOrderedAdditionalServiceRequests;
	}
	
	public void deleteOrderedAdditionalServicesByRentedCarId(int rentedCarId) {
		this.orderedAdditionalServiceDao.deleteAllByRentedCar_RentedCarId(rentedCarId);
	}
	
	public void checkIfOrderedAdditionalServiceExists(int orderedAdditionalServiceId) throws BusinessException {
		
		if(!this.orderedAdditionalServiceDao.existsById(orderedAdditionalServiceId)) {
			throw new BusinessException("No ordered additional service found");
		}
		
	}

}
