package com.rentACar.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentACar.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.rentACar.rentACar.business.dtos.orderedAdditionalServiceDto.OrderedAdditionalServiceListDto;
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

	/*
	 * @PostMapping("/add") public Result add(@RequestBody @Valid
	 * CreateOrderedAdditionalServiceRequest orderedAdditionalServiceRequest) throws
	 * BusinessException { return
	 * this.orderedAdditionalServiceService.add(orderedAdditionalServiceRequest); }
	 * 
	 * @PutMapping("/update") public Result update(@RequestBody @Valid
	 * UpdateOrderedAdditionalServiceRequest additionalServiceRequest) throws
	 * BusinessException { return
	 * this.orderedAdditionalServiceService.update(additionalServiceRequest); }
	 */
	@GetMapping("/getAll")
	public DataResult<List<OrderedAdditionalServiceListDto>> getByRentedCarId(@RequestParam int rentedCarId)
			throws BusinessException {
		return this.orderedAdditionalServiceService.getByRentedCarId(rentedCarId);
	}

	@DeleteMapping
	public Result delete(@RequestParam int orderedAdditionalServiceId) throws BusinessException {
		return this.orderedAdditionalServiceService.delete(orderedAdditionalServiceId);
	}
}
