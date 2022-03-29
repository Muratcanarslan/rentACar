package com.rentACar.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListDto;
import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListForRentedCarDto;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.rentACar.rentACar.core.utilities.exceptions.BusinessException;
import com.rentACar.rentACar.core.utilities.results.DataResult;
import com.rentACar.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalService")
public class OrderedAdditionalServicesController {

	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		super();
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest additionalServiceRequest)
			throws BusinessException {
		return this.orderedAdditionalServiceService.update(additionalServiceRequest);
	}

	@GetMapping("/getByRentedCarId/{rentedCarId}")
	public DataResult<List<OrderedAdditionalServiceListForRentedCarDto>> getByRentedCarId(@RequestParam int rentedCarId)
			throws BusinessException {
		return this.orderedAdditionalServiceService.getByRentedCarId(rentedCarId);
	}

	@GetMapping("/getAll")
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {

		return this.orderedAdditionalServiceService.getAll(pageNo, pageSize);

	}

	@DeleteMapping("/delete")
	public Result delete(
			@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest)
			throws BusinessException {
		return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	}
}
