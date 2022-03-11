package com.rentACar.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListDto;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalService")
public class OrderedAdditionalServicesController {
	
	private OrderedAdditionalServiceService additionalServiceService;

	public OrderedAdditionalServicesController(OrderedAdditionalServiceService additionalServiceService) {
		super();
		this.additionalServiceService = additionalServiceService;
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody UpdateOrderedAdditionalServiceRequest additionalServiceRequest) throws BusinessException {
		return this.additionalServiceService.update(additionalServiceRequest);
	}
	@GetMapping("/getAll")
	public DataResult<List<OrderedAdditionalServiceListDto>> getByRentedCarId(@RequestParam int rentedCarId) throws BusinessException {
		return this.additionalServiceService.getByRentedCarId(rentedCarId);
	}
}
